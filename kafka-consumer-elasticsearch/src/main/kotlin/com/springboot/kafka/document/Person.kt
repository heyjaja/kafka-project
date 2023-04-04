package com.springboot.kafka.document

data class Person(
    var id: String? = null,
    val name: String = "",
    val address: String = ""
)