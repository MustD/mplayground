package com.example.utils

import com.example.module
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

data class TestContext(
    val client: HttpClient,
)

fun testApi(block: suspend TestContext.() -> Unit) = testApplication {
    application { module() }

    val client = createClient {
        install(ContentNegotiation) { json() }
    }

    TestContext(client).block()
}