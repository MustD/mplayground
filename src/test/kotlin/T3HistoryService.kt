package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertTrue

class T3HistoryService {

    @Test
    fun `history chat`() = testApi {

        val clientId = kotlin.random.Random.nextInt()

        suspend fun makeRequest(m: String): HttpResponse = client.post("/history-chat") {
            contentType(ContentType.Application.Json)
            setBody(Message(clientId, m))
        }

        val animal = listOf("Elephant", "Lion", "Cat", "Dog", "Rabbit").random()

        with(makeRequest("Lets assume we have not empty white box. Remember that there is a $animal in white box")) {
            val result = bodyAsText()
            assertTrue { result.isNotEmpty() }
            result.printAnswer()
        }

        with(makeRequest("What is inside the white box? What color is it?")) {
            val result = bodyAsText()
            assertTrue { result.isNotEmpty() }
            result.printAnswer()
        }

        """Given animal: $animal""".printAnswer()
    }

}