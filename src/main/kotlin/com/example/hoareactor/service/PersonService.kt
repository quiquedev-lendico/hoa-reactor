package com.example.hoareactor.service

import com.example.hoareactor.client.AgeClient
import com.example.hoareactor.client.NameClient
import com.example.hoareactor.repository.PersonRepository
import com.example.hoareactor.repository.PersonRepository.Person
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class PersonService(
    private val ageClient: AgeClient,
    private val nameClient: NameClient,
    private val personRepository: PersonRepository
) {

    fun save(ids: Set<String>): Mono<Unit> = ids.stream().forEach(this::save).toMono()

    private fun save(id: String): Mono<Unit> {
        val age = ageClient.retrieve(id).block()!!.age
        val name = nameClient.retrieve(id).block()!!.name
        return personRepository.save(Person(id, name, age)).toMono()
    }
}