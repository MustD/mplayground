package com.example.ai.service

import ai.model.Llama32
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage

interface SimpleChat {
    @SystemMessage(
        """
        You are a helpful assistant. Your answers should be no more than 5 words long.
        """
    )
    fun chat(@UserMessage message: String): String
}

object SimpleService {
    private val model by lazy { Llama32.chatModel }

    val instance: SimpleChat by lazy {
        AiServices.builder(SimpleChat::class.java)
            .chatLanguageModel(model)
//            .moderationModel()
            .build()
    }
}