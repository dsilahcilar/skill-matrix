package com.ing.hr.skillmatrix

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.HttpResponse
import au.com.dius.pact.core.model.Request
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.client.RestTemplate
import java.io.IOException
import kotlin.test.assertEquals


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "ArticlesProvider")
class ExampleJavaConsumerPactTest() {

    @Pact(provider="ArticlesProvider", consumer="test_consumer")
    fun createPact(builder: PactDslWithProvider) : RequestResponsePact {
        return builder
            .given("test state")
            .uponReceiving("ExampleJavaConsumerPactTest test interaction")
            .path("/articles.json")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body("{\"responsetest\": true}")
            .toPact();
    }


    @Test
    @Throws(IOException::class)
    fun test(mockServer: MockServer) {
        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity(mockServer.getUrl() + "/articles.json",String::class.java)
        assertEquals(response.statusCode.value(),200);
    }

}