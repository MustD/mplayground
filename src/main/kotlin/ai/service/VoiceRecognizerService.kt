package com.example.ai.service

import com.example.ai.model.Gemini
import dev.langchain4j.data.audio.Audio
import dev.langchain4j.data.message.AudioContent
import dev.langchain4j.data.message.TextContent
import dev.langchain4j.data.message.UserMessage


object VoiceRecognizerService {
    private val model by lazy { Gemini.chatModel }

    fun recognize(audio: Audio): String {
        val request = UserMessage.from(
            TextContent.from("Please help me to transform this audio into text"),
            AudioContent.from(audio.base64Data(), "audio/mpeg"),
        )
        val response = model.chat(request)
        return response.aiMessage().text()
    }
}