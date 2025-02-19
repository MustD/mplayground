package com.example.ai.service

import ai.model.Llama32
import dev.langchain4j.agent.tool.P
import dev.langchain4j.agent.tool.Tool
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage

interface ToolsChat {
    @SystemMessage(
        """
        You are a helpful assistant. Your answers should be no more than 5 words long.
        """
    )
    fun chat(@MemoryId memoryId: Int, @UserMessage message: String): String
}

class Tools {

    @Tool("Get color of given animal")
    fun getAnimalColors(
        @P("The animal to get color of")
        animal: String,
    ): String = when (animal) {
        "Elephant" -> "green"
        "Lion" -> "brown"
        "Cat" -> "black"
        "Dog" -> "yellow"
        "Rabbit" -> "white"
        else -> "transparent"
    }
}


object ToolService {
    private val model by lazy { Llama32.chatModel }

    val instance: ToolsChat by lazy {
        AiServices.builder(ToolsChat::class.java)
            .chatLanguageModel(model)
            .chatMemoryProvider { _ -> MessageWindowChatMemory.withMaxMessages(10) }
            .tools(Tools()) //adding tool
            .build()
    }
}