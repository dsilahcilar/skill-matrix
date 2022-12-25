package com.ing.hr.skillmatrix;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ArticlesProvider", port = "1234")
public class PactJavaTestNew {
    private Map<String, String> headers = MapUtils.putAll(new HashMap<>(), new String[] {
            "Content-Type", "application/json"
    });

    @BeforeEach
    public void setUp(MockServer mockServer) {
        assertThat(mockServer, is(notNullValue()));
    }

    @Pact(consumer = "ArticlesConsumer")
    public V4Pact articles(PactDslWithProvider builder) {
        return builder
                .given("Articles exist")
                .uponReceiving("retrieving article data")
                .path("/articles.json")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(
                        new PactDslJsonBody()
                                .minArrayLike("articles", 1)
                                .object("variants")
                                .eachKeyLike("0032")
                                .stringType("description", "sample description")
                                .closeObject()
                                .closeObject()
                                .closeObject()
                                .closeArray()
                )
                .toPact(V4Pact.class);
    }


    @Test
    @PactTestFor(pactMethod = "articles")
    void testArticles(MockServer mockServer) throws IOException {
        ClassicHttpResponse httpResponse = (ClassicHttpResponse) Request.get(mockServer.getUrl() + "/articles.json").execute().returnResponse();
        assertThat(httpResponse.getCode(), is(equalTo(200)));
        assertThat(IOUtils.toString(httpResponse.getEntity().getContent()),
                is(equalTo("{\"articles\":[{\"variants\":{\"0032\":{\"description\":\"sample description\"}}}]}")));
    }

}
