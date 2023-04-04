package com.springboot.kafka.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.IndexRequest
import co.elastic.clients.elasticsearch.core.IndexResponse
import co.elastic.clients.elasticsearch.core.SearchResponse
import co.elastic.clients.elasticsearch.core.search.Hit
import co.elastic.clients.elasticsearch.core.search.TotalHits
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation
import com.springboot.kafka.document.Person
import com.springboot.kafka.logger
import org.springframework.stereotype.Service

@Service
class PersonService(
    private val elasticsearchClient: ElasticsearchClient
) {

    fun addDocument(person: Person) {
        val indexRequestBuilder: IndexRequest.Builder<Person> = IndexRequest.Builder<Person>()
        indexRequestBuilder.index("person").document(person)

        val response: IndexResponse = elasticsearchClient.index(indexRequestBuilder.build())

        logger.info { "Indexed with version ${response.id()}" }

    }

    fun searchPerson(keyword: String): List<Person?> {
        val response: SearchResponse<Person> = elasticsearchClient.search(
            { s ->
                s
                    .index("person") // <1>
                    .query { q ->
                        q // <2>
                            .match { t ->
                                t // <3>
                                    .field("name") // <4>
                                    .query(keyword)
                            }
                    }
            },
            Person::class.java // <5>
        )

        val total: TotalHits? = response.hits().total()
        val isExactResult: Boolean = total?.relation() == TotalHitsRelation.Eq

        if (isExactResult) {
            logger.info { "There are ${total?.value()} result" }
        } else {
            logger.info { "There are more than ${total?.value()} results" }
        }

        val hits: List<Hit<Person>> = response.hits().hits()

        val result: List<Person?> = hits.map {
            val person: Person? = it.source()
            person?.id = it.id()

            logger.info("Found person ${it.source()}, score ${it.score()}" )

            person ?: null
        }

        return result

    }
}