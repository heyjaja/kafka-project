package com.springboot.kafka

import com.launchdarkly.eventsource.MessageEvent
import com.launchdarkly.eventsource.background.BackgroundEventHandler
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate


class WikimediaChangesHandler(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val topic: String
) : BackgroundEventHandler {

    private val logger = KotlinLogging.logger {}

    override fun onOpen() {
        TODO("Not yet implemented")
    }

    override fun onClosed() {
        TODO("Not yet implemented")
    }

    override fun onMessage(event: String?, messageEvent: MessageEvent?) {
        logger.info { "event data -> ${ messageEvent?.data }" }

        kafkaTemplate.send(topic, messageEvent?.data)
    }

    override fun onComment(comment: String?) {
        TODO("Not yet implemented")
    }

    override fun onError(t: Throwable?) {
        TODO("Not yet implemented")
    }
}