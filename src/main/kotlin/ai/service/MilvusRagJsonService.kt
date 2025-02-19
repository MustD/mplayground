package com.example.ai.service

import ai.model.Llama32
import ai.model.NomicEmbedText
import ai.store.MilvusStore
import dev.langchain4j.data.document.Document
import dev.langchain4j.data.document.splitter.DocumentByLineSplitter
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.request.ChatRequest
import dev.langchain4j.model.chat.request.ChatRequestParameters
import dev.langchain4j.model.chat.request.json.JsonObjectSchema
import dev.langchain4j.model.chat.request.json.JsonSchema
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.rag.query.Query


object MilvusRagJsonService {
    private val milvusCollection = "animals4json_collection"
    private val model by lazy { Llama32.chatModel }
    private val store by lazy { MilvusStore(collectionName = milvusCollection).store }
    private val embeddingModel by lazy { NomicEmbedText.embeddingModel }

    private val contentRetriever by lazy {
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(store)
            .embeddingModel(embeddingModel)
            .build()
    }

    private val schema by lazy {
        JsonSchema.builder()
            .name("Animal")
            .rootElement(
                JsonObjectSchema.builder()
                    .addStringProperty("Type")
                    .addStringProperty("Color")
                    .addStringProperty("Shape")
                    .addStringProperty("Name")
                    .required("Type", "Color", "Shape", "Name")
                    .build()
            )
            .build()
    }


    fun chat(message: String): String {
        val userMessage = UserMessage.from(message)

        Query.from(message)
        val retriever = EmbeddingStoreContentRetriever.builder()
            .embeddingStore(store)
            .embeddingModel(embeddingModel)
            .build()

        //https://github.com/langchain4j/langchain4j-examples/blob/main/rag-examples/src/main/java/_3_advanced/_02_Advanced_RAG_with_Query_Routing_Example.java
        //https://docs.langchain4j.dev/tutorials/rag#core-rag-apis
//        LanguageModelQueryRouter(model, retriever)

        val chatRequest = ChatRequest.builder()
            .parameters(
                ChatRequestParameters.builder()
                    .responseFormat(schema)
                    .build()
            )
            .messages(
                userMessage
            )
            .build()

        return model.chat(chatRequest).aiMessage().text()
    }

    fun addEmbeddings(text: String) {
        val document = Document.from(text)
        val split = DocumentByLineSplitter(256, 256).split(document)
        val embedding = embeddingModel.embedAll(split).content()
        store.addAll(embedding, split)
    }
}