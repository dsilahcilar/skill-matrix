package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.databuilder.DataGenerators
import com.ing.hr.skillmatrix.databuilder.ORGANIZATIONS
import com.ing.hr.skillmatrix.dto.Employee
import com.ing.hr.skillmatrix.dto.Organization
import com.ing.hr.skillmatrix.dto.Project
import com.ing.hr.skillmatrix.dto.Role
import org.springframework.stereotype.Service

@Service
class DTOInitilizationService(
    private val employeeService: EmployeeService,
    private val projectService: ProjectService,
    private val organizationService: OrganizationService,
    private val roleService: RoleService,
    private val dataGenerators: DataGenerators
) {

    fun addOrganizations() {
        ORGANIZATIONS.map {
            organizationService.addOrganization(Organization(name = it))
        }.forEach { eachOrganization ->
            addEmployees(eachOrganization)
            addProjects(eachOrganization.id!!)
        }
    }

    private fun addProjects(organizationID: Long) {
        (1..5).map {
            projectService.add(Project(dataGenerators.project.generate()))
        }.forEach {
            organizationService.addProject(organizationID, it)
        }
    }

    private fun addEmployees(eachOrganization: Organization?) {
        (1..5).map {
            employeeService.add(newEmployee()).id
        }.forEach { employeeId ->
            organizationService.addEmployee(employeeId!!, eachOrganization!!.id!!)
            val role = getRandomRole()
            employeeService.addRole(employeeId, role)
            role.skills?.forEach { skill ->
                employeeService.addSkill(employeeId, skill.name, (1..5).random())
            }
        }
    }

    private fun getRandomRole(): Role {
        return roleService.getRoles().random()
    }

    private fun newEmployee(): Employee {
        val firstName = dataGenerators.name.generate()
        val lastName = dataGenerators.lastName.generate()
        return Employee(firstName = firstName, lastName = lastName, email = "${firstName}.${lastName}@ing.com")
    }

}

