package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.data.RoleRepository
import com.ing.hr.skillmatrix.databuilder.EmployeeBuilder
import com.ing.hr.skillmatrix.databuilder.NameGenerator
import com.ing.hr.skillmatrix.databuilder.OrganizationBuilder
import com.ing.hr.skillmatrix.dto.Employee
import com.ing.hr.skillmatrix.dto.Organization
import com.ing.hr.skillmatrix.dto.Role
import org.springframework.stereotype.Service


@Service
class DTOInitilizationService(
    private val organizationBuilder: OrganizationBuilder,
    private val employeeBuilder: EmployeeBuilder,
    private val roleRepository: RoleRepository,
    private val nameGenerator: NameGenerator
) {
    private lateinit var trade: Organization
    private lateinit var lending: Organization
    private var employees = mutableListOf<Employee>()

    private var WB_ID: Long? = null
        get() {
            if (field == null) {
                WB_ID = organizationBuilder.getOrganizations().find { it.name == "Wholesale Banking" }?.id
            }
            return field
        }

    fun addOrganizations(): DTOInitilizationService {
        trade = organizationBuilder.addOrganization(Organization(name = "trade")).organization!!

        lending = organizationBuilder.addOrganization(Organization(name = "lending")).organization!!

        organizationBuilder
            .addSubOrganization(WB_ID!!, trade)
            .addSubOrganization(WB_ID!!, lending)

        val channelDigitalization =
            organizationBuilder.addOrganization(Organization(name = "Channel Digitalization")).organization
        val interfaceDigitalization =
            organizationBuilder.addOrganization(Organization(name = "Interface Digitalization")).organization

        organizationBuilder
            .addSubOrganization(trade.id!!, channelDigitalization!!)
            .addSubOrganization(trade.id!!, interfaceDigitalization!!)

        return this

    }

    fun addEmployess(): DTOInitilizationService {
        organizationBuilder.getOrganizations().forEach { eachOrganization ->
            (1..5).map {
                newEmployee()
            }.map {
                employeeBuilder.add(it).employee
            }.forEach {
                employees.add(it!!)
                organizationBuilder.addEmployee(it.id!!, eachOrganization.id!!)
            }
        }
        return this
    }

    fun addRoles(): DTOInitilizationService {
        employees.forEach { employee ->
            employeeBuilder.addRole(employee.id!!, getRandomRole())
        }
        return this
    }

    private fun getRandomRole(): Role {
        var roles = roleRepository.findAll()
        return roles[(0..roles.size - 1).random()].toDTO()
    }

    private fun newEmployee(): Employee {
        val firstName = nameGenerator.generateRandomName()
        val lastName = nameGenerator.generateRandomName()
        return Employee(firstName = firstName, lastName = lastName, email = "${firstName}.${lastName}@ing.com")
    }

}

