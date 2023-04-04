package com.springboot.kafka

import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.background.BackgroundEventHandler
import com.launchdarkly.eventsource.background.BackgroundEventSource
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.net.URI
import java.util.concurrent.TimeUnit

@Service
class WikimediaChangesProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    private val logger = KotlinLogging.logger {}

    fun sendMessage() {
        val topic: String = "wikimedia_recentchange"

        // to read real time stream data from wikimedia, we use event source
        val eventHandler: BackgroundEventHandler = WikimediaChangesHandler(kafkaTemplate = kafkaTemplate, topic = topic)
        val url: String = "https://stream.wikimedia.org/v2/stream/recentchange"

        val builder: BackgroundEventSource.Builder = BackgroundEventSource.Builder(eventHandler, EventSource.Builder(URI.create(url)))
        val eventSource: BackgroundEventSource = builder.build()

        eventSource.start()

        TimeUnit.SECONDS.sleep(5)

    }

}