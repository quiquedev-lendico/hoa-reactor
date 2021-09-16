package com.example.hoareactor.repository

import com.example.ReactorUtils.safeMono
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import kotlin.jvm.Throws

class PersonRepository {

    fun save(person: Person): Mono<Unit> =
        Mono.fromCallable { println("saving $person into db") }
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))


    fun save2(person: Person): Mono<Unit> = safeMono {
        jooqCall()
    }

    fun String.toSnakeCase(): String = TODO()

    fun <T> Mono<T>.enrique(): Mono<T> =
        this.map {
            println("hey im enrique")
            it
        }


    @Throws(IllegalAccessError::class)
    private fun jooqCall(): Unit = TODO()

    data class Person(val id: String, val name: String, val age: Int)

}