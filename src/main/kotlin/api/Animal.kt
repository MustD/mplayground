package com.example.api

import com.example.ai.service.api.AnimalOutput
import kotlinx.serialization.Serializable

@Serializable
class Animal(
    val name: String,
    val type: String,
    val shape: String,
    val color: String,
)

fun fromOutput(animal: AnimalOutput) = Animal(
    name = animal.name,
    type = animal.type,
    shape = animal.shape,
    color = animal.color,
)