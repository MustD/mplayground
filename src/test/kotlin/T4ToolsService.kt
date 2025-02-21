package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertTrue

class T4ToolsService {

    @Test
    fun `tools chat`() = testApi {

        val clientId = kotlin.random.Random.nextInt()

        suspend fun makeRequest(m: String): HttpResponse = client.post("/tools-chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, m))
        }

        val animal = listOf("Elephant", "Lion", "Cat", "Dog", "Rabbit").random()

        with(makeRequest("Put the $animal in red box")) {
            val result = bodyAsText()
            assertTrue { result.isNotEmpty() }
            result.printAnswer()
        }

        with(makeRequest("What animal inside the red box? What color is the animal?")) {
            val result = bodyAsText()
            assertTrue { result.isNotEmpty() }
            result.printAnswer()
        }

        """Given animal: $animal""".printAnswer()
    }

}