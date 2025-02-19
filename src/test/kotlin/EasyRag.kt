package com.example

import com.example.chat.Message
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test

class EasyRag {

    @Test
    fun easyRagTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val clientId = kotlin.random.Random.nextInt()

        val response = client.post("/easy-rag/fairytale") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Who is Cubey?"))
        }
        println(response.bodyAsText())
    }

}