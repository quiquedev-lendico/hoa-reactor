package com.example.hoareactor.client

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class AgeClient (
    private val webClient: WebClient
){
    @Throws(Exception::class)
    fun retrieve(id: String): Mono<AgeDto> =
        webClient.get()
            .uri { uriBuilder -> uriBuilder.path("/person/age/${id}").build(id) }
            .retrieve()
            .bodyToMono(AgeDto::class.java)

    data class AgeDto(
        val id: String,
        val age: Int
    ) {
        fun isValid(): Boolean = age > 18
    }
}