package com.example.ai.tools

import dev.langchain4j.agent.tool.Tool

class Information {

    @Tool
    fun getLatestKotlinVersion(): String {
        return "2.1.10"
    }

}