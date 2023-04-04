package com.springboot.kafka.document

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface WikimediaRepository : ElasticsearchRepository<WikimediaEventSource, String>