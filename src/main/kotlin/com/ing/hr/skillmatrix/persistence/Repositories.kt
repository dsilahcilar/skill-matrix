package com.ing.hr.skillmatrix.persistence

import au.com.dius.pact.core.support.json.JsonToken
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


interface CloudRepository : CrudRepository<OrganizationEntity, Long> {
    @Query(
        "select new com.ing.hr.skillmatrix.persistence.SkillCloud(skill.name, round(avg(emp_skill.level), 2))\n" +
                "from SkillEntity skill\n" +
                "join EmployeeSkillEntity emp_skill on skill.id = emp_skill.skill.id \n" +
                "group by skill.name"
    )
    fun calculateEmployeeSkills(): List<SkillCloud>

    @Query(
        "select new com.ing.hr.skillmatrix.persistence.SkillCloud(skill.name, round(avg(projectSkill.level), 2)) \n" +
                "from ProjectSkillEntity projectSkill\n" +
                "join SkillEntity skill on projectSkill.skill.id = skill.id \n" +
                "group by skill.name"
    )
    fun calculateRequiredSkills(): List<SkillCloud>
}

data class SkillCloud(val text: String, val value: Double)