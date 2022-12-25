package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.Organization
import com.ing.hr.skillmatrix.dto.OrganizationBasic
import com.ing.hr.skillmatrix.dto.OrganizationDetailed
import com.ing.hr.skillmatrix.service.OrganizationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/organizations")
class OrganizationController(val organizationService: OrganizationService) {

    @GetMapping
    @JsonView(OrganizationBasic::class)
    fun getOrganizations(): List<Organization> {
        return organizationService.getOrganizations()
    }

    @GetMapping("/{id}")
    @JsonView(OrganizationDetailed::class)
    fun getOrganization(@PathVariable id: Long): Organization {
        return organizationService.getOrganization(id)
    }

    @PostMapping
    @JsonView(OrganizationDetailed::class)
    fun addOrganization(@RequestBody organization: Organization) {
        organizationService.addOrganization(organization)
    }

    @PutMapping("/{id}/suborganizations")
    @JsonView(OrganizationDetailed::class)
    fun addSubOrganization(@PathVariable parentId: Long, @RequestBody organization: Organization) {
        organizationService.addSubOrganization(parentId, organization)
    }
}
