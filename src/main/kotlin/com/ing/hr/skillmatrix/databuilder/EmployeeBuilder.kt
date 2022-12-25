package com.ing.hr.skillmatrix.databuilder

import com.ing.hr.skillmatrix.dto.Employee
import com.ing.hr.skillmatrix.dto.Role
import com.ing.hr.skillmatrix.service.EmployeeService
import org.springframework.stereotype.Service

@Service
class EmployeeBuilder(
    private val employeeService: EmployeeService
) {
    var employee: Employee? = null

    fun add(employee: Employee): EmployeeBuilder {
        this.employee = employeeService.add(employee)
        return this
    }

    fun addRole(employeeID: Long, role: Role): EmployeeBuilder {
        val newEmployee = Employee(role = role)
        employeeService.updateEmployee(employeeID, newEmployee)
        return this
    }

}
