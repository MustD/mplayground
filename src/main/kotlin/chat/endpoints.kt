package com.example.chat

import com.example.ai.models.Llama32
import com.example.ai.services.Assistant
import dev.langchain4j.data.message.SystemMessage.systemMessage
import dev.langchain4j.data.message.UserMessage.userMessage
import dev.langchain4j.model.chat.chat
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val chatId: Int = 0,
    val message: String = "",
)

fun Route.chat() {

    post("/low-level/chat") {
        val message = call.receive<Message>()
        val answer = CoroutineScope(Dispatchers.IO).async {
            Llama32.model.chat {
                messages += systemMessage("You are a helpful assistant. Your answers should be no more than 5 words long.")
                messages += userMessage(message.message)
//                parameters(DefaultChatRequestParameters.builder()){ }
            }
        }
        call.respondText { answer.await().aiMessage().text() }
    }

    post("/ai-service/chat") {
        val message = call.receive<Message>()

        val answer = Assistant.instance.chat(message.chatId, message.message)
        call.respondText { answer }
    }


}