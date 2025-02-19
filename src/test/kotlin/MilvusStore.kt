package com.example

import com.example.chat.Document
import com.example.chat.Message
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test

class MilvusStore {

    @Test
    fun documentInsertTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        client.post("milvus/insert") {
            contentType(ContentType.Application.Json)
            setBody(Document("Squirrel named Squeaky have brown and triangle shape."))
        }
    }

    @Test
    fun messageSearchTest() = testApplication {
        application { module() }
        val client = createClient { install(ContentNegotiation) { json() } }

        val response = client.post("milvus/search") {
            contentType(ContentType.Application.Json)
            setBody(Message(1, "What animal is Squeaky, and what shape and color is it?"))
        }
        println(response.bodyAsText())
    }
}