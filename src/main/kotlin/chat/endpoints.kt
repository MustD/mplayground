package com.example.chat

import com.example.ai.services.Assistant
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val chatId: Int = 0,
    val message: String = "",
)

fun Route.chat() {

    post("/chat") {
        val message = call.receive<Message>()

        val answer = Assistant.instance.chat(message.chatId, message.message)
        call.respondText { answer }
    }
}