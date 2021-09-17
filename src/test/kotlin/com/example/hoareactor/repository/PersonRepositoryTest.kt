package com.example.hoareactor.repository

import arrow.core.some
import com.example.hoareactor.WithDatabaseContainer
import com.example.hoareactor.configuration.CommonConfiguration
import com.example.hoareactor.configuration.RepositoryConfiguration
import com.example.hoareactor.repository.tables.references.PERSON
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import java.math.BigDecimal
import java.util.*

@SpringBootTest(
    classes = [CommonConfiguration::class, RepositoryConfiguration::class]
)
@EnableAutoConfiguration(
    exclude = [R2dbcAutoConfiguration::class]
)
class PersonRepositoryTest : WithDatabaseContainer {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Autowired
    lateinit var dslContext: DSLContext

    @Test
    fun `test findOneById`() {
        val personRecord = dslContext.newRecord(PERSON)
        personRecord.id = UUID.randomUUID().toString()
        personRecord.name = "test name"
        personRecord.age = BigDecimal.valueOf(24)
        personRecord.store()

        val result = personRepository.findById(personRecord.id!!).block()

        assertThat(personRecord.some()).isEqualTo(result)
    }

    @Test
    fun `test save`() {
        // given
        val personRecord = dslContext.newRecord(PERSON)
        personRecord.id = UUID.randomUUID().toString()
        personRecord.name = "test name"
        personRecord.age = BigDecimal.valueOf(24)
        // when
        personRepository.save(personRecord).block()!!

        // then
        val stored = dslContext.selectFrom(PERSON)
            .where(PERSON.ID.eq(personRecord.id))
            .fetchOne()

        assertThat(personRecord).isEqualTo(stored)
    }
}