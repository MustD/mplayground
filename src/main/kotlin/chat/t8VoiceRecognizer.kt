package com.example.chat

import com.example.ai.service.VoiceRecognizerService
import dev.langchain4j.data.audio.Audio
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
fun Route.voiceRecognizer() {

    post("/recognize-voice") {
        val fileBytes: ByteArray = call.receive<ByteArray>()

        if (fileBytes.isEmpty()) return@post call.respondText("No file received")
        fileBytes.let { bytes ->
            val audio = Audio.builder()
                .base64Data(Base64.Default.encode(bytes))
                .build()

            val answer = VoiceRecognizerService.recognize(audio)
            call.respondText { answer }
        }
    }

}