package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.Employee
import com.ing.hr.skillmatrix.dto.EmployeeDetailed
import com.ing.hr.skillmatrix.service.EmployeeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employees")
class EmployeeController(val employeeService: EmployeeService) {

    @PostMapping
    @JsonView(EmployeeDetailed::class)
    fun add(@RequestBody employee: Employee): Employee {
        return employeeService.add(employee)
    }

    @JsonView(EmployeeDetailed::class)
    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: Long): Employee {
        return employeeService.getEmployee(id)
    }

    @JsonView(EmployeeDetailed::class)
    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody employee: Employee): Employee {
        return employeeService.updateEmployee(id, employee)
    }
}

