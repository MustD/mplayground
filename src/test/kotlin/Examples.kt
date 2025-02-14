package com.example

import com.example.chat.Message
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertNotNull

class Examples {

    @Test
    fun aiServiceTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val clientId = kotlin.random.Random.nextInt()

        val response = client.post("/ai-service/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Whats best programming language?"))
        }
        println(response.bodyAsText())
    }

    @Test
    fun lowLevelApiTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val clientId = kotlin.random.Random.nextInt()

        val response = client.post("/low-level/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Whats best programming language?"))
        }
        println(response.bodyAsText())
    }

    @Test
    fun historyTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val clientId = kotlin.random.Random.nextInt()

        val response1 = client.post("/ai-service/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "I want you to remember that there is a rabbit in the white box"))
        }
        println(response1.bodyAsText())

        val response2 = client.post("/ai-service/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Please remind me what is in the white box"))
        }

        println(response2.bodyAsText())
        assertNotNull(response2.bodyAsText())
    }

    @Test
    fun toolTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }
        val clientId = kotlin.random.Random.nextInt()

        val response1 = client.post("/ai-service/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "What is the latest version of Kotlin"))
        }
        println(response1.bodyAsText())

    }

}