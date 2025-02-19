package com.example.chat

import com.example.ai.service.HistoryService
import com.example.api.Message
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.historyChat() {

    post("/history-chat") {
        val message = call.receive<Message>()

        val answer = HistoryService.instance.chat(message.chatId, message.message)

        call.respondText { answer }
    }

}