package com.example.chat

import com.example.ai.service.ToolService
import com.example.api.Message
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.toolsChat() {

    post("/tools-chat") {
        val message = call.receive<Message>()

        val answer = ToolService.instance.chat(message.chatId, message.message)

        call.respondText { answer }
    }

}