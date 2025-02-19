package com.example.ai.services

import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage

interface Chat {

    @SystemMessage(
        """
           You are a helpful assistant. 
           Your answers should be no more than 2 sentences long.
        """
    )
    fun chat(@MemoryId memoryId: Int, @UserMessage request: String): String


    @SystemMessage(
        """
            You are helpful assistant.
            You can help the user by providing information from documentation.
        """
    )
    fun documentation(@MemoryId memoryId: Int, @UserMessage request: String): String

}