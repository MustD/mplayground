package com.example.ai.models

import dev.langchain4j.model.ollama.OllamaChatModel

object DeepseekR1 {
    private val MODEL_NAME: String = "deepseek-r1:1.5b" // try other local ollama model names
    private val BASE_URL: String = "http://localhost:11434" // local ollama base url

    val model: OllamaChatModel = OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .build()
}