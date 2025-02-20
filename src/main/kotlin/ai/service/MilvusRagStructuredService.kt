package com.example.ai.service

import ai.model.Llama32
import ai.model.NomicEmbedText
import ai.store.MilvusStore
import com.example.ai.service.api.AnimalOutput
import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.splitter.DocumentByLineSplitter
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage

interface MilvusRagStructuredChat {
    @SystemMessage(
        """
            You are specialist in fairy tale animals.
            You can easy find any animal, its shape and color.
        """
    )
    fun animalSearch(@MemoryId memoryId: Int, @UserMessage message: String): AnimalOutput
}


object MilvusRagStructuredService {
    private val milvusCollection = "animals_collection_structured"
    private val model by lazy { Llama32.chatModel }
    private val store by lazy { MilvusStore(collectionName = milvusCollection).store }
    private val embeddingModel by lazy { NomicEmbedText.embeddingModel }

    private val contentRetriever by lazy {
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(store)
            .embeddingModel(embeddingModel)
            .build()
    }

    val instance: MilvusRagStructuredChat by lazy {
        AiServices.builder(MilvusRagStructuredChat::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
            .contentRetriever(contentRetriever)
            .build()
    }

    fun addEmbeddings(text: String) {
        val document = Document.from(text)
        val split = DocumentByLineSplitter(1024, 256).split(document)
        val embedding = embeddingModel.embedAll(split).content()
        store.addAll(embedding, split)
    }
}