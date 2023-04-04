package com.springboot.kafka.controller

import com.springboot.kafka.document.Person
import com.springboot.kafka.service.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    private val personService: PersonService
) {
    @PostMapping
    fun addDocument(@RequestBody person: Person): ResponseEntity<String> {
        personService.addDocument(person = person)
        return ResponseEntity.ok("ok")
    }

    @GetMapping
    fun searchPerson(@RequestParam("keyword") keyword: String): ResponseEntity<List<Person?>>
        =  ResponseEntity.ok(personService.searchPerson(keyword = keyword))
}