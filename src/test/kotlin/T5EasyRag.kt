package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertTrue

class T5EasyRag {

    @Test
    fun `easy rag`() = testApi {

        val clientId = kotlin.random.Random.nextInt()

        val fairytale = run {
            this.javaClass.classLoader.getResource("fairytale.txt")?.readText() ?: error("Can't load 'fairytale.txt'")
        }

        //insert every time, as embedding stored in memory
        client.post("/easy-rag/insert") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, fairytale))
        }

        val response = client.post("/easy-rag/search") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Who is Dot?"))
        }

        val result = response.bodyAsText()
        assertTrue { result.isNotEmpty() }
        result.printAnswer()
    }

}