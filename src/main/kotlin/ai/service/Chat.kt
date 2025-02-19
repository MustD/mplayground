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
            You are specialist in fairy tale animals.
            You can easy find any animal, its shape and color.
            Your answers should be no more than 5 words long.
        """
    )
    fun animalSearch(@MemoryId memoryId: Int, @UserMessage request: String): String

}