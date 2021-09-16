package com.example.hoareactor.repository

import reactor.core.publisher.Mono

class PersonRepository {

    fun save(person: Person): Mono<Unit> = TODO()

    data class Person(val id: String, val name: String, val age: Int)
}