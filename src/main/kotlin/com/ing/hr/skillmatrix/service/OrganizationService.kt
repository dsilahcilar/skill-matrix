package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.data.*
import com.ing.hr.skillmatrix.dto.EmployeeSkill
import com.ing.hr.skillmatrix.dto.Organization
import com.ing.hr.skillmatrix.dto.Project
import org.springframework.stereotype.Service

@Service
class OrganizationService(
    private val organizationRepository: OrganizationRepository,
    private val employeeRepository: EmployeeRepository
) {

    fun addEmployee(employeeID: Long, organizationID: Long) {
        val organization = organizationRepository.findById(organizationID).get()
        val employee = employeeRepository.findById(employeeID).get()
        organization.addEmployee(employee)
        organizationRepository.save(organization)
    }

    fun addOrganization(organization: Organization): Organization {
        return organizationRepository.save(OrganizationEntity(organization.name)).toDTO()
    }

    fun addSubOrganization(parentId: Long, organization: Organization): Organization {
        val parentOrganization = organizationRepository.findById(parentId).get()
        var childOrganization = OrganizationEntity(organization.name)
        if (organization.id != null) {
            childOrganization = organizationRepository.findById(organization.id).get()
        }
        parentOrganization.addSubOrganization(childOrganization)
        return organizationRepository.save(parentOrganization).toDTO()
    }

    fun getOrganizations(): List<Organization> {
        val organizationEntities = organizationRepository.findAll()
        return organizationEntities.map { it.toDTO() }
    }

    fun getOrganization(id: Long): Organization {
        val organization = organizationRepository.findById(id)
        return organization.get().toDTO()
    }

}


fun OrganizationEntity.toDTO(): Organization {
    return Organization(
        this.id!!,
        this.name,
        this.parent?.id,
        emptyList(),
        employeeList.map { it.toDTO() },
        this.projectList.map { it.toDTO() }
    )
}

fun EmployeeSkillEntity.toDTO(): EmployeeSkill {
    return EmployeeSkill(this.skill.name, this.level)
}

fun ProjectEntity.toDTO(): Project {
    return Project(this.name)
}
