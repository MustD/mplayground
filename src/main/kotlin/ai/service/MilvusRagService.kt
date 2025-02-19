package com.example.ai.service

import ai.model.Llama32
import ai.model.NomicEmbedText
import ai.store.MilvusStore
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage

interface MilvusRagChat {
    @SystemMessage(
        """
            You are specialist in fairy tale animals.
            You can easy find any animal, its shape and color.
            Your answers should be no more than 10 words long and contain animal name, animal shape and animal color.
        """
    )
    fun animalSearch(@MemoryId memoryId: Int, @UserMessage message: String): String
}


object MilvusRagService {
    private val model by lazy { Llama32.chatModel }
    private val store by lazy { MilvusStore().store }
    private val embeddingModel by lazy { NomicEmbedText.embeddingModel }

    private val contentRetriever by lazy {
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(store)
            .embeddingModel(embeddingModel)
            .build()
    }

    val instance: MilvusRagChat by lazy {
        AiServices.builder(MilvusRagChat::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
            .contentRetriever(contentRetriever)
            .build()
    }

    fun addEmbeddings(text: String) {
        val embedding = embeddingModel.embed(text).content()
        store.add(embedding, TextSegment.from(text))
    }
}