package com.ing.hr.skillmatrix.controller

import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactFolder
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc


@WebMvcTest
@Provider("SkillMatrixProvider")
@PactFolder("contracts")
internal class OrganizationEntityControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider::class)
    fun getOrganization(pactContext: PactVerificationContext) {
        pactContext.verifyInteraction()
    }

    @BeforeEach
    fun before(context: PactVerificationContext) {
        context.target = MockMvcTestTarget(mockMvc)
    }

    @State("organization with some users")
    fun `organization with some users`() {
    }
}