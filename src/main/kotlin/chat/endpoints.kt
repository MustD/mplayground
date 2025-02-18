package com.example.chat

import com.example.ai.models.Llama32
import com.example.ai.services.Assistant
import com.example.ai.services.Chat
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.message.SystemMessage.systemMessage
import dev.langchain4j.data.message.UserMessage.userMessage
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.chat.chat
import dev.langchain4j.model.chat.request.DefaultChatRequestParameters
import dev.langchain4j.model.ollama.OllamaEmbeddingModel
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.Serializable


@Serializable
data class Message(
    val chatId: Int = 0,
    val message: String = "",
)

@Serializable
data class Document(
    val content: String = "",
)

fun Route.chat() {

    post("/low-level/chat") {
        val message = call.receive<Message>()
        val answer = CoroutineScope(Dispatchers.IO).async {
            Llama32.model.chat {
                messages += systemMessage("You are a helpful assistant. Your answers should be no more than 5 words long.")
                messages += userMessage(message.message)
                parameters(DefaultChatRequestParameters.builder()) { }
            }
        }
        call.respondText { answer.await().aiMessage().text() }
    }

    post("/service/chat") {
        val message = call.receive<Message>()

        val answer = Assistant.instance.chat(message.chatId, message.message)
        call.respondText { answer }
    }

    post("/easy-rag/fairytale") {
        val message = call.receive<Message>()

        val document =
            FileSystemDocumentLoader.loadDocument("/home/md/IdeaProjects/mlplayground/src/main/resources/fairytale.txt")
        val store = InMemoryEmbeddingStore<TextSegment>()

        EmbeddingStoreIngestor.ingest(document, store)

        val instance: Chat = AiServices.builder(Chat::class.java)
            .chatLanguageModel(Llama32.model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
            .contentRetriever(EmbeddingStoreContentRetriever.from(store))
            .build()

        val answer = instance.documentation(message.chatId, message.message)
        call.respondText { answer }
    }

    post("/milvus/insert") {
        val document = call.receive<Document>()

        val embeddingStore: EmbeddingStore<TextSegment> = MilvusEmbeddingStore.builder()
            .uri("http://127.0.0.1:19530")
            .collectionName("animals_collection_8")
            .dimension(768)
            .build()

        val embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("nomic-embed-text:latest")
            .build()

        val embedding = embeddingModel.embed(document.content).content()
        val result = embeddingStore.add(embedding, TextSegment.from(document.content))
        call.respondText { result }

    }

    post("/milvus/search") {
        val message = call.receive<Message>()

        val embeddingStore: EmbeddingStore<TextSegment> = MilvusEmbeddingStore.builder()
            .uri("http://127.0.0.1:19530")
            .collectionName("animals_collection_8")
            .build()

        val embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("nomic-embed-text:latest")
            .build()

        val contentRetriever = EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .build()

        val instance: Chat = AiServices.builder(Chat::class.java)
            .chatLanguageModel(Llama32.model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
            .contentRetriever(contentRetriever)
            .build()

        val answer = instance.documentation(message.chatId, message.message)
        call.respondText { answer }
    }


}