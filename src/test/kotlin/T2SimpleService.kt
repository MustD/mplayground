package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertTrue

class T2SimpleService {

    @Test
    fun `service chat`() = testApi {

        val clientId = kotlin.random.Random.nextInt()

        val response = client.post("/service-chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, "What is the capital of Great Britain?"))
        }

        val result = response.bodyAsText()

        assertTrue { result.isNotEmpty() }
        result.printAnswer()
    }

}