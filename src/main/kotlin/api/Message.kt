package com.example.api

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val chatId: Int = 0,
    val message: String = "",
)