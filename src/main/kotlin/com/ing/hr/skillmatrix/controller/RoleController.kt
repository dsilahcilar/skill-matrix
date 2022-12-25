package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.Role
import com.ing.hr.skillmatrix.dto.RoleBasic
import com.ing.hr.skillmatrix.dto.RoleDetailed
import com.ing.hr.skillmatrix.service.RoleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/roles")
class RoleController(private val roleService: RoleService) {

    @GetMapping
    @JsonView(RoleBasic::class)
    fun getRoles(): List<Role> {
        return roleService.getRoles()
    }

    @GetMapping("/{id}")
    @JsonView(RoleDetailed::class)
    fun getRole(@PathVariable id: Long): Role {
        return roleService.getRole(id)
    }

    @PostMapping
    @JsonView(RoleBasic::class)
    fun addOrganization(@RequestBody role: Role): Role {
        return roleService.add(role.name!!)
    }
}

