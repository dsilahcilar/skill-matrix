package com.ing.hr.skillmatrix.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SkillRepository : JpaRepository<SkillEntity, Long> {
    fun findByName(name: String): Optional<SkillEntity>
}

interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): Optional<RoleEntity>
}

interface EmployeeRepository : JpaRepository<EmployeeEntity, Long>


interface OrganizationRepository : JpaRepository<OrganizationEntity, Long> {
    fun findByParentNull() : List<OrganizationEntity>
    fun findByName(name: String) : List<OrganizationEntity>
}

interface ProjectRepository : JpaRepository<ProjectEntity, Long>