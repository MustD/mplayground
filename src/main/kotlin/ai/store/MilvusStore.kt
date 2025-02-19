package com.example.ai.store

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore


class MilvusStore(
    private val collectionName: String = "animals_collection",
    private val embeddingDimension: Int = 768,
) {
    private val url: String = "http://127.0.0.1:19530"

    val store: EmbeddingStore<TextSegment> by lazy { createEmbeddingStore() }

    private fun createEmbeddingStore(): EmbeddingStore<TextSegment> = MilvusEmbeddingStore.builder()
        .uri(url)
        .collectionName(collectionName)
        .dimension(embeddingDimension)
        .build()
}