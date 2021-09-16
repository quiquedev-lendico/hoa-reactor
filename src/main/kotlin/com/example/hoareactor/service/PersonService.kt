package com.example.hoareactor.service

import com.example.hoareactor.client.AgeClient
import com.example.hoareactor.client.AgeClient.AgeDto
import com.example.hoareactor.client.NameClient
import com.example.hoareactor.client.NameClient.NameDto
import com.example.hoareactor.repository.PersonRepository
import com.example.hoareactor.repository.PersonRepository.Person
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class PersonService(
    private val ageClient: AgeClient,
    private val nameClient: NameClient,
    private val personRepository: PersonRepository
) {

    private companion object {
        val SCHEDULER = Schedulers.boundedElastic()
    }

    fun save(ids: Set<String>): Mono<Unit> {

        val step1: Flux<String> = Flux
            .fromIterable(ids)
        // string1, string2, string3, ...

        val step2: Flux<Unit> = step1
            .parallel(5)
            .runOn(SCHEDULER)
            .flatMap(::save)
            .sequential()
        // Unit, Unit, Unit, ...

        val step3: Mono<List<Unit>> = step2.collectList()

        return step3.map { }
    }

    private fun save(id: String): Mono<Unit> {
        val nameMono: Mono<NameDto> = nameClient.retrieve(id)
            .subscribeOn(SCHEDULER)
        val ageMono: Mono<AgeDto> = ageClient.retrieve(id)
            .subscribeOn(SCHEDULER)

        val nameAndAgeMono: Mono<Pair<NameDto, AgeDto>> =
            nameMono.zipWith(ageMono) { name, age ->
                Pair(name, age)
            }

        return nameAndAgeMono
            .flatMap { (name, age) ->
                personRepository.save(
                    Person(
                        id,
                        name.name,
                        age.age
                    )
                )
            }
    }
}