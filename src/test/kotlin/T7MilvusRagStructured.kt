package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

class T7MilvusRagStructured {

    @Test
    @Ignore
    fun `insert content into Milvus`() = testApi {
        val fairytale = run {
            this.javaClass.classLoader.getResource("fairytale.txt")?.readText() ?: error("Can't load 'fairytale.txt'")
        }

        val clientId = kotlin.random.Random.nextInt()

        val response = client.post("/milvus-rag-structured/insert") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, fairytale))
        }
        assertTrue { response.status.isSuccess() }
    }

    @Test
    fun `milvus rag structured`() = testApi {
        val clientId = kotlin.random.Random.nextInt()

        val response = client.post("/milvus-rag-structured/search") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "Who is Prism?"))
        }

        val result = response.bodyAsText()
        assertTrue { result.isNotEmpty() }
        result.printAnswer()
    }

}