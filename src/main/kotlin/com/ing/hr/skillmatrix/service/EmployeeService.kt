package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.data.EmployeeEntity
import com.ing.hr.skillmatrix.data.EmployeeRepository
import com.ing.hr.skillmatrix.data.RoleRepository
import com.ing.hr.skillmatrix.dto.Employee
import org.springframework.stereotype.Service


@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val roleRepository: RoleRepository
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