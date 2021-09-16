package com.example.hoareactor.client

import com.example.hoareactor.configuration.CommonConfiguration
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import reactor.test.StepVerifier

@SpringBootTest
@Import(CommonConfiguration::class)
class NameClientTest {

    @Autowired
    lateinit var nameClient: NameClient

    lateinit var wireMockServer: WireMockServer

    @BeforeAll
    fun configureWiremock() {
        configureFor("http://name-service.com/", 8080)
    }

    @Test
    fun `test retrieve name data`() {
        wireMockServer.start()
        stubFor(get(anyUrl()).willReturn(okJson("""{"id": "id-1","name": "enrique"}""")))

        val nameResponse = nameClient.retrieve("id-1")
        val expectedResult = NameClient.NameDto("id-1", "enrique")

        // then
        StepVerifier.create(nameResponse)
            .consumeNextWith { name ->
                assertThat(name).isEqualTo(expectedResult)
            }
            .verifyComplete()
        wireMockServer.stop()
    }
}