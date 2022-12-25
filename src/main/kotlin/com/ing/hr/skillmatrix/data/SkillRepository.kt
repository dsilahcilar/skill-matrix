package com.ing.hr.skillmatrix.data

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SkillRepository : JpaRepository<SkillEntity, Long> {
}

interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): Optional<RoleEntity>
}

interface EmployeeRepository : JpaRepository<EmployeeEntity, Long> {
}

interface OrganizationRepository : JpaRepository<OrganizationEntity, Long> {
}