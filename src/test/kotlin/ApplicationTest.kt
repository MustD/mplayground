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

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val clientId = kotlin.random.Random.nextInt()

        val response1 = client.post("/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "I want you to remember that there is a rabbit in the white box"))
        }
        println(response1.bodyAsText())

        val response2 = client.post("/chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Please remind me what is in the white box"))
        }

        println(response2.bodyAsText())
        assertNotNull(response2.bodyAsText())
    }

}
