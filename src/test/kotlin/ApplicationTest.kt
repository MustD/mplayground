package com.example

import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.Test
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun testRoot() = testApi {

        val response = client.get("/")

        assertTrue { response.bodyAsText() == "Hello World!" }
        response.bodyAsText().printAnswer()
    }

}
