package com.example.hoareactor.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class PersonRouter(
    private val handler: PersonRestHandler
) {

    @Bean
    fun save(): RouterFunction<ServerResponse> = route(
        POST("/").and(accept(APPLICATION_JSON)), handler::handleSave
    )
}