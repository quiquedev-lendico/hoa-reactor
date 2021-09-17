package com.example.hoareactor.configuration

import com.example.hoareactor.client.AgeClient
import com.example.hoareactor.client.NameClient
import com.example.hoareactor.repository.PersonRepository
import com.example.hoareactor.rest.PersonRestHandler
import com.example.hoareactor.service.PersonService
import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CommonConfiguration {

    @Bean
    fun nameClient(webClientBuilder: WebClient.Builder) = NameClient(
        webClient = webClientBuilder.baseUrl("http://name-service.com/").build()
    )

    @Bean
    fun ageClient(webClientBuilder: WebClient.Builder) = AgeClient(
        webClient = webClientBuilder.baseUrl("http://age-service.com/").build()
    )

    @Bean
    fun personRepository(dslContext: DSLContext) = PersonRepository(
        dslContext = dslContext,
    )

    @Bean
    fun personService(nameClient: NameClient, ageClient: AgeClient, personRepository: PersonRepository) = PersonService(
        nameClient = nameClient,
        ageClient = ageClient,
        personRepository = personRepository
    )

    @Bean
    fun personHandler(personService: PersonService) = PersonRestHandler(
        personService = personService
    )
}