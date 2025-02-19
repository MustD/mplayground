package com.example.ai.services

import com.example.ai.models.Llama32
import com.example.ai.tools.Information
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.service.AiServices

object Assistant {
    val instance: Chat = AiServices.builder(Chat::class.java)
        .chatLanguageModel(Llama32.chatModel)
        .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
        .tools(Information())
        .build()
}