package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.*
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

    @GetMapping("/{id}/employees")
    @JsonView(EmployeeDetailed::class)
    fun getEmployeeSkills(@PathVariable id: Long): List<Employee>? {
        return organizationService.getEmployees(id)
    }

    @GetMapping("/{id}/projects")
    @JsonView(ProjectList::class)
    fun getProjects(@PathVariable id: Long): List<Project>? {
        return organizationService.getOrganization(id).projects
    }


    @PostMapping
    @JsonView(OrganizationDetailed::class)
    fun addOrganization(@RequestBody organization: Organization) {
        organizationService.addOrganization(organization)
    }

    @PutMapping("/{id}/suborganizations")
    @JsonView(OrganizationDetailed::class)
    fun addSubOrganization(@PathVariable id: Long, @RequestBody organization: Organization) {
        organizationService.addSubOrganization(id, organization)
    }
}
