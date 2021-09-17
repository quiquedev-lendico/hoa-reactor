package com.example.hoareactor.repository

import arrow.core.Option
import arrow.core.toOption
import com.example.ReactorUtils.safeMono
import com.example.hoareactor.repository.RepositoryErrors.DatabaseError
import com.example.hoareactor.repository.tables.records.PersonRecord
import com.example.hoareactor.repository.tables.references.PERSON
import org.jooq.DSLContext
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

class PersonRepository(
    private val dslContext: DSLContext,
) {

    fun findById(id: String): Mono<Option<PersonRecord>> =
        safeMono {
            dslContext.selectFrom(PERSON)
                .where(PERSON.ID.eq(id))
                .fetchOne()
                .toOption()
        }.onErrorMap(::DatabaseError)

    fun save(personRecord: PersonRecord): Mono<Unit> =
        safeMono {
            personRecord.store()
            Unit
        }.onErrorMap(::DatabaseError)

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