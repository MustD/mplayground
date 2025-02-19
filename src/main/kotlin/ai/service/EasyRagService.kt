package com.example.ai.service

import ai.model.Llama32
import dev.langchain4j.data.document.Document
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore

interface EasyRagChat {
    @SystemMessage(
        """
            You are specialist in fairy tale animals.
            You can easy find any animal, its shape and color.
            Your answers should be no more than 10 words long and contain animal name, animal shape and animal color.
        """
    )
    fun animalSearch(@MemoryId memoryId: Int, @UserMessage message: String): String
}


object EasyRagService {
    private val model by lazy { Llama32.chatModel }
    private val store by lazy { InMemoryEmbeddingStore<TextSegment>() }

    val instance: EasyRagChat by lazy {
        AiServices.builder(EasyRagChat::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
            .contentRetriever(EmbeddingStoreContentRetriever.from(store))
            .build()
    }

    fun addEmbeddings(text: String) {
        val document = Document.from(text)
        EmbeddingStoreIngestor.ingest(document, store)
    }
}