package com.example.chat

import com.example.ai.service.SimpleService
import com.example.api.Message
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.serviceChat() {

    post("/service-chat") {
        val message = call.receive<Message>()

        val answer = SimpleService.instance.chat(message.message)

        call.respondText { answer }
    }

}