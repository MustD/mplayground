package com.example

import com.example.api.Message
import com.example.utils.printAnswer
import com.example.utils.testApi
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.measureTimedValue

class T9Parallel {

    @Test
    fun `simple chat`() = testApi {

        val countries = listOf(
            "Canada" to "Ottawa",
            "France" to "Paris",
            "Japan" to "Tokyo",
            "Australia" to "Canberra",
            "Brazil" to "Brasilia",
            "Egypt" to "Cairo",
            "Italy" to "Rome",
            "Nigeria" to "Abuja",
            "Argentina" to "Buenos Aires",
            "Peru" to "Lima",
            "Spain" to "Madrid",
            "Germany" to "Berlin",
            "United Kingdom" to "London",
            "India" to "New Delhi",
            "South Korea" to "Seoul",
            "Mexico" to "Mexico City",
            "Indonesia" to "Jakarta",
            "Turkey" to "Ankara",
            "Saudi Arabia" to "Riyadh",
            "Switzerland" to "Bern",
            "Sweden" to "Stockholm",
            "Norway" to "Oslo",
            "Denmark" to "Copenhagen",
            "Finland" to "Helsinki",
            "Netherlands" to "Amsterdam",
            "Belgium" to "Brussels",
            "Austria" to "Vienna",
            "Portugal" to "Lisbon",
            "Greece" to "Athens",
            "Poland" to "Warsaw",
            "Ireland" to "Dublin",
            "New Zealand" to "Wellington",
            "Chile" to "Santiago",
            "Colombia" to "Bogota",
            "Venezuela" to "Caracas",
            "Thailand" to "Bangkok",
            "Vietnam" to "Hanoi",
            "Malaysia" to "Kuala Lumpur",
            "Philippines" to "Manila",
            "Singapore" to "Singapore",
            "Russia" to "Moscow",
            "Ukraine" to "Kyiv",
            "Czech Republic" to "Prague",
            "Hungary" to "Budapest",
            "Romania" to "Bucharest",
            "Israel" to "Jerusalem",
            "Jordan" to "Amman",
            "United Arab Emirates" to "Abu Dhabi",
            "Qatar" to "Doha",
            "Morocco" to "Rabat",
            "Kenya" to "Nairobi",
            "Ghana" to "Accra",
            "Ethiopia" to "Addis Ababa",
            "Algeria" to "Algiers",
            "Pakistan" to "Islamabad",
            "Bangladesh" to "Dhaka",
            "Iran" to "Tehran",
            "Cuba" to "Havana",
            "Mongolia" to "Ulaanbaatar"
        ).shuffled()

        fun createRequest(country2capital: Pair<String, String>): Deferred<Pair<String, String>> {
            val clientId = Random.nextInt()
            return async(start = CoroutineStart.LAZY) {
                client.post("/simple-chat") {
                    contentType(ContentType.Application.Json)
                    setBody(Message(clientId, "What is the capital of ${country2capital.first}?"))
                }.bodyAsText() to country2capital.second
            }
        }

        val requests = countries.map(::createRequest)

        val parallel = true
        val timedValue = measureTimedValue {
            run {
                if (parallel) requests.awaitAll() else requests.map { it.await() }
            }.joinToString("") { (answer, correct) -> "|${correct.padEnd(14)}| $answer \n" }
        }

        timedValue.duration.toString().printAnswer()

        val result = timedValue.value
        assertTrue { result.isNotEmpty() }
        result.printAnswer()
    }
}