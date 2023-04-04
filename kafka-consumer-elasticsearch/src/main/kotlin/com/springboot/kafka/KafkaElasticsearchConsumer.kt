package com.springboot.kafka

import com.springboot.kafka.document.WikimediaEventSource
import com.springboot.kafka.document.WikimediaRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaElasticsearchConsumer(
    private val wikimediaRepository: WikimediaRepository
) {

    @KafkaListener(
        topics = ["wikimedia_recentchange"],
        groupId = "test"
    )
    fun consume(eventMessage: String) {
        logger.info { "Event message received -> $eventMessage" }

        val eventSource: WikimediaEventSource = WikimediaEventSource(null, eventMessage)

        wikimediaRepository.save(eventSource)
    }
}