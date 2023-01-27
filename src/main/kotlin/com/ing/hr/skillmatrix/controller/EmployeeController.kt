package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.Employee
import com.ing.hr.skillmatrix.dto.EmployeeDetailed
import com.ing.hr.skillmatrix.dto.EmployeeSkill
import com.ing.hr.skillmatrix.dto.EmployeeSkillRequest
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

    @JsonView(EmployeeDetailed::class)
    @GetMapping("/{id}/skills")
    fun updateEmployee(@PathVariable id: Long): List<EmployeeSkill>? {
        return employeeService.getEmployee(id).skills
    }

    @JsonView(EmployeeDetailed::class)
    @PostMapping("/{id}/skills")
    fun updateEmployee(@PathVariable id: Long, @RequestBody employeeSkillRequest: List<EmployeeSkillRequest>): Employee {
        return employeeService.updateSkills(id, employeeSkillRequest)
    }
}

