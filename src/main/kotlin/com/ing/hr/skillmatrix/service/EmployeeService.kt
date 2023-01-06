package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.dto.Employee
import com.ing.hr.skillmatrix.dto.Role
import com.ing.hr.skillmatrix.persistence.*
import org.springframework.stereotype.Service


@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val roleRepository: RoleRepository,
    private val skillRepository: SkillRepository
) {

    fun add(employee: Employee): Employee {
        return employeeRepository.save(EmployeeEntity(employee.firstName!!, employee.lastName!!, employee.email!!))
            .toDTO()
    }

    fun getEmployee(id: Long): Employee {
        return employeeRepository.findById(id).get().toDTO()
    }

    fun updateEmployee(id: Long, updateRequest: Employee): Employee {
        val employee = employeeRepository.findById(id).get()
        val role = roleRepository.findById(updateRequest.role!!.id!!).get()
        employee.role(role)
        return employeeRepository.save(employee).toDTO()
    }

    fun addSkill(id: Long, skill: String, level: Int): Employee {
        val employee = employeeRepository.findById(id).get()
        val skill = skillRepository.findByName(skill).get()
        employee.addSkill(EmployeeSkillEntity(skill = skill, level = level))
        return employeeRepository.save(employee).toDTO()
    }

    fun addRole(employeeID: Long, role: Role): Employee {
        val newEmployee = Employee(role = role)
        return updateEmployee(employeeID, newEmployee)
    }

}

fun EmployeeEntity.toDTO(): Employee {
    return Employee(
        this.id,
        this.firstName,
        this.lastName,
        this.email,
        toRole(this.role),
        this.organization?.id,
        this.skills.map { it.toDTO() }
    )
}