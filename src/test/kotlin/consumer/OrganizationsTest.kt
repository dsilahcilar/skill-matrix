package consumer

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.*
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.client.RestTemplate
import java.io.IOException
import kotlin.test.assertEquals


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "SkillMatrixProvider")
class OrganizationsTest() {


    @Pact(provider="SkillMatrixProvider", consumer="ui_consumer")
    fun createNewOrganization(builder: PactDslWithProvider) : V4Pact {

        val body = newJsonArray {
            newObject {
                integerType("userId","10001")
                stringValue("name","Deniz")
                stringValue("lastName","Silahcilar")
                stringValue("email","deniz.silahcilar@ing.com")
                stringValue("role","Engineer V")
                integerType("teamId","123")
                stringValue("photoURL","https://mdbootstrap.com/img/new/avatars/4.jpg%27")
            }
            newObject {
                integerType("userId","10002")
                stringValue("name","Latif Ihsan")
                stringValue("lastName","Bulut")
                stringValue("email","latif.bulut@ing.com")
                stringValue("role","Engineer 3")
                integerType("teamId","123")
                stringValue("photoURL","https://mdbootstrap.com/img/new/avatars/4.jpg%27")
            }
        }


        return builder
            .given("organization with some users")
            .uponReceiving("get an organization by id")
            .path("/organizations/123")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(body)
            .toPact()
            .asV4Pact()
            .unwrap()
    }


    @Test
    @Throws(IOException::class)
    fun test(mockServer: MockServer) {
        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity(mockServer.getUrl() + "/organizations/123",String::class.java)
        assertEquals(response.statusCode.value(),200);
    }

}