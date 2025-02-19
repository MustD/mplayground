package com.example.ai.models

import dev.langchain4j.model.ollama.OllamaChatModel

object Llama32 {
    private val MODEL_NAME: String = "llama3.2:3b" // try other local ollama model names
    private val BASE_URL: String = "http://localhost:11434" // local ollama base url

    val chatModel: OllamaChatModel by lazy {
        OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .build()
    }
}