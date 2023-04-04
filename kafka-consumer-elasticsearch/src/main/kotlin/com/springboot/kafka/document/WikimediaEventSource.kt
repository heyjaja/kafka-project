package com.springboot.kafka.document

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "test")
data class WikimediaEventSource(
    @Id
    val id: String? = null,
    val data: String
)