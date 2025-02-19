package com.example.chat

import ai.model.Llama32
import com.example.api.Message
import dev.langchain4j.data.message.SystemMessage.systemMessage
import dev.langchain4j.data.message.UserMessage.userMessage
import dev.langchain4j.model.chat.chat
import dev.langchain4j.model.chat.request.DefaultChatRequestParameters
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


fun Route.simpleChat() {

    post("/simple-chat") {
        val message = call.receive<Message>()
        val answer = CoroutineScope(Dispatchers.IO).async {

            val systemMessage = systemMessage(
                """
                    You are a helpful assistant. Your answers should be no more than 5 words long.
                """.trimIndent()
            )
            Llama32.chatModel.chat {
                messages += systemMessage
                messages += userMessage(message.message)
                parameters(DefaultChatRequestParameters.builder()) { }
            }

        }
        call.respondText { answer.await().aiMessage().text() }
    }

}