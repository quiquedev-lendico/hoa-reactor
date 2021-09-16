package com.example.hoareactor.rest

import com.example.hoareactor.service.PersonService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class PersonRestHandler(
    private val personService: PersonService
) {

    fun handleSave(serverRequest: ServerRequest): Mono<ServerResponse> = TODO()
//    {
//        val ids = serverRequest.bodyToMono(PersonRequestDto::class.java).block().ids
//        personService.save(ids)
//    }

    data class PersonRequestDto(
        val ids: Set<String>
    )
}