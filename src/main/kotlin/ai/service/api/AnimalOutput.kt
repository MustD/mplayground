package com.example.ai.service.api

import dev.langchain4j.model.output.structured.Description

@Description("An animal from fairy tale")
class AnimalOutput(
    @Description("The name of the animal")
    val name: String,
    @Description("The spices of the animal")
    val type: String,
    @Description("The shape of the animal")
    val shape: String,
    @Description("The color of the animal")
    val color: String,
)