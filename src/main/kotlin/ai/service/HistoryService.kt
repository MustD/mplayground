package com.example.ai.service

import ai.model.Llama32
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage

interface HistoryChat {
    @SystemMessage(
        """
        You are a helpful assistant. Your answers should be no more than 5 words long.
        """
    )
    fun chat(@MemoryId memoryId: Int, @UserMessage message: String): String
}

object HistoryService {
    private val model by lazy { Llama32.chatModel }

    val instance: HistoryChat by lazy {
        AiServices.builder(HistoryChat::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) } //adding 10 messages to remember
            .build()
    }
}