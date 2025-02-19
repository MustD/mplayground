package com.example

import com.example.chat.Document
import com.example.chat.Message
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import java.io.File
import kotlin.test.Test

class MilvusStore {

    @Test
    fun documentInsertTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val content = File("src/main/resources/fairytale.txt").readText()

        client.post("milvus/insert") {
            contentType(ContentType.Application.Json)
            setBody(Document(content))
        }
    }

    @Test
    fun messageSearchTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val response = client.post("milvus/search") {
            contentType(ContentType.Application.Json)
            setBody(Message(1, "Who is Conrad?"))
        }
        println(response.bodyAsText())
    }
}