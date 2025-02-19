package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertTrue

class T6MilvusRag {

    @Test
    fun `milvus rag`() = testApi {

        val clientId = kotlin.random.Random.nextInt()

        val fairytale = run {
            this.javaClass.classLoader.getResource("fairytale.txt")?.readText() ?: error("Can't load 'fairytale.txt'")
        }

//        client.post("/easy-rag/insert") {
//            contentType(ContentType.Application.Json)
//            setBody(Message(clientId, fairytale))
//        }

        val response = client.post("/milvus-rag/search") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Who is Zip?"))
        }

        val result = response.bodyAsText()
        assertTrue { result.isNotEmpty() }
        result.printAnswer()
    }

}