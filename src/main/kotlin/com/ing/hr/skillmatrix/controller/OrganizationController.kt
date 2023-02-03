package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.*
import com.ing.hr.skillmatrix.persistence.EmployeeSkillCloud
import com.ing.hr.skillmatrix.persistence.EmployeeSkillCloudRepository
import com.ing.hr.skillmatrix.service.OrganizationService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/organizations")
class OrganizationController(val organizationService: OrganizationService, val employeeSkillCloudRepository: EmployeeSkillCloudRepository) {

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
    fun getEmployees(@PathVariable id: Long): List<Employee>? {
        return organizationService.getEmployees(id)
    }

    @GetMapping("/{id}/profiles")
    fun getEmployeeProfiles(@PathVariable id: Long): List<EmployeeProfile>? {
       return organizationService.getUserProfiles(id)
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

    @GetMapping("/{id}/metrics")
    @JsonView(OrganizationMetricList::class)
    fun organizationMetrics(@PathVariable id: Long): List<OrganizationMetric> {
        return organizationService.getOrganizationMetric(id)
    }

    @GetMapping("/totalEmployeeSkills")
    fun totalSkills(): List<EmployeeSkillCloud> {
        return employeeSkillCloudRepository.calculateEmployeeSkills()
    }
}
