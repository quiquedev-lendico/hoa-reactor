package com.example.hoareactor.client

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class NameClient(
    private val webClient: WebClient
) {

    @Throws(Exception::class)
    fun retrieve(id: String): Mono<NameDto> =
        webClient.get()
            .uri { uriBuilder -> uriBuilder.path("/person/name/${id}").build(id) }
            .retrieve()
            .bodyToMono(NameDto::class.java)

    data class NameDto(
        val id: String,
        val name: String
    )
}