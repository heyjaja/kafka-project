package com.springboot.kafka

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaProducerApplication(
	private val wikimediaChangesProducer: WikimediaChangesProducer
) : CommandLineRunner {

	override fun run(vararg args: String?) {
		wikimediaChangesProducer.sendMessage()
	}
}

fun main(args: Array<String>) {
	runApplication<KafkaProducerApplication>(*args)
}
