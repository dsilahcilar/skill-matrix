package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.dto.*
import com.ing.hr.skillmatrix.persistence.*
import org.springframework.stereotype.Service

@Service
class OrganizationService(
    private val organizationRepository: OrganizationRepository,
    private val employeeRepository: EmployeeRepository,
    private val projectRepository: ProjectRepository
) {

    fun organizationTree(): OrganizationTree {
        val rootOrganization = organizationRepository.findByParentNull().first()
        return OrganizationTree(
            rootOrganization.id!!,
            rootOrganization.name,
            buildSubOrganizations(rootOrganization.subOrganizations)
        )
    }

    fun buildSubOrganizations(subOrganizationsEntity: List<OrganizationEntity>): List<OrganizationTree> {
        return subOrganizationsEntity.map {
            OrganizationTree(
                it.id!!,
                it.name,
                buildSubOrganizations(it.subOrganizations)
            )
        }
    }


    fun addEmployee(employeeID: Long, organizationID: Long) {
        val organization = organizationRepository.findById(organizationID).get()
        val employee = employeeRepository.findById(employeeID).get()
        organization.addEmployee(employee)
        organizationRepository.save(organization)
    }

    fun addOrganization(organization: Organization): Organization {
        return organizationRepository.save(
            OrganizationEntity(organization.name)
                .addProjects(ProjectEntity("Default"))
        ).toDTO()
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

    fun getEmployees(id: Long): List<Employee>? {
        val organization = getOrganization(id)
        val minSkillMap = getMinSkillForAnOrganization(organization)
        val employees = organization.employees
        employees?.forEach { employee ->
            employee.skills?.forEach { eachSkill ->
                eachSkill.minLevel = minSkillMap.getOrDefault(eachSkill.skill, -1)
            }
        }
        return employees
    }

    fun getMinSkillForAnOrganization(organization: Organization): HashMap<String, Int> {
        val minSkillLevelMap = hashMapOf<String, Int>()
        organization.projects?.forEach { eachProject ->
            eachProject.projectSkills.forEach { projectSkill ->
                val skill = projectSkill.skill.name
                if (minSkillLevelMap.containsKey(skill)) {
                    if (minSkillLevelMap.get(skill)!! < projectSkill.level) {
                        minSkillLevelMap.put(skill, projectSkill.level)
                    }
                } else {
                    minSkillLevelMap.put(skill, projectSkill.level)
                }
            }
        }
        return minSkillLevelMap
    }

    fun addNewProject(organizationID: Long, project: Project): Organization {
        val organization = organizationRepository.findById(organizationID).get()
        organization.addProject(ProjectEntity(project.name))
        return organizationRepository.save(organization).toDTO()
    }

    fun addProject(organizationID: Long, projectId: Long): Organization {
        val organization = organizationRepository.findById(organizationID).get()
        val projectEntity = projectRepository.findById(projectId).get()
        organization.addProject(projectEntity)
        return organizationRepository.save(organization).toDTO()
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

