package com.ing.hr.skillmatrix.controller.bff

import com.ing.hr.skillmatrix.service.OrganizationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/organizationtree")
@RestController
class OrganizationTreeController(val organizationService: OrganizationService) {

    @GetMapping
    fun getOrganizationTree() = organizationService.organizationTree()
}