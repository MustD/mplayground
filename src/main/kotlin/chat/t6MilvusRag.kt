package com.example.chat

import com.example.ai.service.MilvusRagService
import com.example.api.Message
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.milvusRag() {

    post("/milvus-rag/insert") {
        val message = call.receive<Message>()
        MilvusRagService.addEmbeddings(message.message)
        call.respondText { "ok" }
    }

    post("/milvus-rag/search") {
        val message = call.receive<Message>()

        val answer = MilvusRagService.instance.animalSearch(message.chatId, message.message)

        call.respondText { answer }
    }

}