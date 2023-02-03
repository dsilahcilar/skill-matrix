package com.ing.hr.skillmatrix.controller.bff

import com.ing.hr.skillmatrix.persistence.CloudRepository
import com.ing.hr.skillmatrix.persistence.SkillCloud
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

@RequestMapping("/cloud")
@RestController
class CloudController(val cloudRepository: CloudRepository) {

    @GetMapping("/employees")
    fun employeeSkills(): List<SkillCloud> {
        return cloudRepository.calculateEmployeeSkills()
    }

    @GetMapping("/required")
    fun requiredSkills(): List<SkillCloud> {
        return cloudRepository.calculateRequiredSkills()
    }
}