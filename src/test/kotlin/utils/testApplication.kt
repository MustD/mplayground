package com.example.utils

import com.example.module
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

data class TestContext(
    val client: HttpClient,
    override val coroutineContext: CoroutineContext = client.coroutineContext,
) : CoroutineScope

fun testApi(block: suspend TestContext.() -> Unit) = testApplication {
    application { module() }

    val client = createClient {
        install(ContentNegotiation) { json() }
    }

    TestContext(client).block()
}