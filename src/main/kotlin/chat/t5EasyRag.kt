package com.example.chat

import com.example.ai.service.EasyRagService
import com.example.api.Message
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.easyRag() {

    post("/easy-rag/insert") {
        val message = call.receive<Message>()
        EasyRagService.addEmbeddings(message.message)
        call.respondText { "ok" }
    }

    post("/easy-rag/search") {
        val message = call.receive<Message>()

        val answer = EasyRagService.instance.animalSearch(message.chatId, message.message)

        call.respondText { answer }
    }

}