package com.ing.hr.skillmatrix.dto

import com.fasterxml.jackson.annotation.JsonView


interface OrganizationBasic
interface OrganizationDetailed : OrganizationBasic

data class Organization(
    @JsonView(OrganizationBasic::class)
    val id: Long? = null,
    @JsonView(OrganizationBasic::class)
    val name: String,
    @JsonView(OrganizationBasic::class)
    val parentId: Long? = null,
    @JsonView(OrganizationDetailed::class)
    val subOrganizations: List<Organization>? = null,
    @JsonView(OrganizationDetailed::class)
    val employees: List<Employee>? = null,
    @JsonView(OrganizationDetailed::class)
    val projects: List<Project>? = null,
)

interface EmployeeBasic
interface EmployeeDetailed : EmployeeBasic

data class Employee(
    @JsonView(EmployeeBasic::class, OrganizationDetailed::class, RoleBasic::class)
    val id: Long? = null,
    @JsonView(EmployeeBasic::class, OrganizationDetailed::class, RoleBasic::class)
    val firstName: String? = null,
    @JsonView(EmployeeBasic::class, OrganizationDetailed::class, RoleBasic::class)
    val lastName: String? = null,
    @JsonView(EmployeeBasic::class, OrganizationDetailed::class, RoleBasic::class)
    val email: String? = null,
    @JsonView(EmployeeBasic::class)
    val role: Role? = null,
    @JsonView(RoleBasic::class)
    val organizationId: Long? = null,
    @JsonView(EmployeeDetailed::class)
    val skills: List<EmployeeSkill>? = null,
)


interface RoleBasic
interface RoleDetailed : RoleBasic

data class Role(
    @JsonView(RoleBasic::class, EmployeeBasic::class)
    val id: Long? = null,
    @JsonView(RoleBasic::class, EmployeeBasic::class)
    val name: String?,
    @JsonView(RoleBasic::class)
    val skills: List<Skill>?,
    @JsonView(RoleDetailed::class)
    val employee: List<Employee>?
)

data class Skill(
    @JsonView(RoleBasic::class)
    val name: String,
    val roles: List<Role>
)

@JsonView(EmployeeDetailed::class)
data class EmployeeSkill(
    val skill: String,
    val level: Int
)

data class Project(
    @JsonView(OrganizationDetailed::class)
    val name: String
)

//TODO: Project and projectSkills relationships should be added to DTO init class.
data class ProjectSkill(
    val project: Project,
    val skill: Skill,
    val level: Int
)

