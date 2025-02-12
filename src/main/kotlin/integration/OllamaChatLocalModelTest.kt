package com.example.integration

import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.chat.request.ResponseFormat.JSON
import dev.langchain4j.model.ollama.OllamaChatModel


object OllamaChatLocalModelTest {
    private val MODEL_NAME: String = "deepseek-r1:1.5b" // try other local ollama model names
    private val BASE_URL: String = "http://localhost:11434" // local ollama base url

    fun run(args: Array<String>) {
        var model: ChatLanguageModel = OllamaChatModel.builder()
            .baseUrl(BASE_URL)
            .modelName(MODEL_NAME)
            .build()
        val answer = model.chat("List top 10 cites in China")
        println(answer)

        model = OllamaChatModel.builder()
            .baseUrl(BASE_URL)
            .modelName(MODEL_NAME)
            .responseFormat(JSON)
            .build()

        val json = model.chat("List top 10 cites in US")
        println(json)
    }
}