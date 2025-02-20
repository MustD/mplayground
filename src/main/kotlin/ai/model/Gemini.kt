package com.example.ai.model

import com.example.Config
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel

object Gemini {
    private val MODEL_NAME: String = "gemini-2.0-flash"
    private val API_KEY: String by lazy { Config.geminiApiKey }

    val chatModel: GoogleAiGeminiChatModel by lazy {
        GoogleAiGeminiChatModel.builder()
            .apiKey(API_KEY)
            .modelName(MODEL_NAME)
            .build()
    }
}