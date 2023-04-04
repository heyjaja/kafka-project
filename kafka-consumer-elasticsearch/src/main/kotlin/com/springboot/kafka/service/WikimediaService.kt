package com.springboot.kafka.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.IndexRequest
import co.elastic.clients.elasticsearch.core.IndexResponse
import com.springboot.kafka.document.WikimediaEventSource
import com.springboot.kafka.logger
import org.springframework.stereotype.Service

@Service
class WikimediaService(
    private val elasticsearchClient: ElasticsearchClient
) {
    fun addDocument(eventSource: WikimediaEventSource) {
        val indexRequestBuilder: IndexRequest.Builder<WikimediaEventSource> = IndexRequest.Builder<WikimediaEventSource>()
        indexRequestBuilder.index("wikimedia").document(eventSource)

        val response: IndexResponse = elasticsearchClient.index(indexRequestBuilder.build())

        logger.info { "Indexed with version ${response.id()}" }

    }
}