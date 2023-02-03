package com.ing.hr.skillmatrix.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SkillRepository : JpaRepository<SkillEntity, Long> {
    fun findByName(name: String): Optional<SkillEntity>
}

interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): Optional<RoleEntity>
}

interface EmployeeRepository : JpaRepository<EmployeeEntity, Long>


interface OrganizationRepository : JpaRepository<OrganizationEntity, Long> {
    fun findByParentNull(): List<OrganizationEntity>
    fun findByName(name: String): List<OrganizationEntity>
}

interface ProjectRepository : JpaRepository<ProjectEntity, Long>

interface InterviewReposity : CrudRepository<InterviewEntity, Long> {
    fun findByKeyword(keyword: String): InterviewEntity?
}


interface EmployeeSkillCloudRepository : CrudRepository<OrganizationEntity, Long> {
    @Query(
        "select new com.ing.hr.skillmatrix.persistence.EmployeeSkillCloud(skill.name, sum(emp_skill.level))\n" +
                "from SkillEntity skill\n" +
                "join EmployeeSkillEntity emp_skill on skill.id = emp_skill.skill.id \n" +
                "group by skill.name"
    )
    fun calculateEmployeeSkills(): List<EmployeeSkillCloud>
}

data class EmployeeSkillCloud(val name: String, val value: Long)