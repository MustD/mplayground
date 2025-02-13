package com.example.ai.services

import com.example.ai.models.DeepseekR1
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.service.AiServices

object Assistant {
    val instance: Chat = AiServices.builder(Chat::class.java)
        .chatLanguageModel(DeepseekR1.model)
        .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
        .build()
}