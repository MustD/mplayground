package com.example

import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

class T8VoiceService {

    @Test
    fun `recognise voice`() = testApi {
        val audio = run {
            this.javaClass.classLoader.getResource("example.mp3") ?: error("Can't load 'example.mp3'")
        }

        val response = client.post("recognize-voice") {
            setBody(File(audio.toURI()).readBytes())
        }

        val result = response.bodyAsText()

        assertTrue { result.isNotEmpty() }
        result.printAnswer()
    }

}