package com.example.ai.model

import dev.langchain4j.model.ollama.OllamaEmbeddingModel

object NomicEmbedText {
    private val MODEL_NAME: String = "nomic-embed-text:latest" // try other local ollama model names
    private val BASE_URL: String = "http://localhost:11434" // local ollama base url

    val embeddingModel: OllamaEmbeddingModel by lazy {
        OllamaEmbeddingModel.builder()
            .baseUrl(BASE_URL)
            .modelName(MODEL_NAME)
            .build()
    }

}