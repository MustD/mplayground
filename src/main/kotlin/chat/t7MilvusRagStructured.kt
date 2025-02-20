package com.example.chat

import com.example.ai.service.MilvusRagStructuredService
import com.example.api.Message
import com.example.api.fromOutput
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.milvusRagStructured() {

    post("/milvus-rag-structured/insert") {
        val message = call.receive<Message>()
        MilvusRagStructuredService.addEmbeddings(message.message)
        call.respondText { "ok" }
    }

    post("/milvus-rag-structured/search") {
        val message = call.receive<Message>()

        val answer = MilvusRagStructuredService.instance.animalSearch(message.chatId, message.message)
        val response = fromOutput(answer)

        call.respond(response)
    }

}