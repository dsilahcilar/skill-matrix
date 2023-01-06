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


    @Pact(provider = "SkillMatrixProvider", consumer = "ui_consumer")
    fun getOneOrganization(builder: PactDslWithProvider): V4Pact {

        val body = newJsonArray {
            newObject {
                integerType("id", "10001")
                stringValue("name", "Wholesale Banking")
                integerType("parentId", 1)
                eachArrayLike("employees") {
                    newObject {
                        integerType("id", "1")
                        stringType("firstName", "Deniz")
                        stringType("lastName", "Silahcilar")
                        stringType("email", "Deniz.Silahcilar@ing.com")
                    }
                }

            }
        }


        return builder
            .given("organization with some users")
            .uponReceiving("get an organization by id")
            .matchPath("/organizations/[0-9]+","/organizations/10001")
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
        val response = restTemplate.getForEntity(mockServer.getUrl() + "/organizations/10001", String::class.java)
        assertEquals(response.statusCode.value(), 200);
    }

}