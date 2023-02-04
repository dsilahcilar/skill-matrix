package com.ing.hr.skillmatrix.dto

import com.fasterxml.jackson.annotation.JsonView

data class ProjectSkillRequest(
    val skillId: Long,
    val minLevel: Int
)

data class EmployeeSkillRequest(
    val skillId: Long,
    val level: Int
)


interface OrganizationMetricList

@JsonView(OrganizationMetricList::class)
data class OrganizationMetric(
    @JsonView(OrganizationMetricList::class)
    val organizationId: Long? = null,
    @JsonView(OrganizationMetricList::class)
    val teamName: String,
    @JsonView(OrganizationMetricList::class)
    val numberOfRequiredSkills: Int = 0,
    @JsonView(OrganizationMetricList::class)
    val numberOfEmployees: Int = 0,
    @JsonView(OrganizationMetricList::class)
    val numberOfProjects: Int = 0,
    val sumOfSkillLevels: Double = 0.0,
    @JsonView(OrganizationMetricList::class)
    val teamMaturityLevel: Double = 0.0,
    @JsonView(OrganizationMetricList::class)
    val numberOfMissingSkills: Int = 0
)


data class EmployeeProfile(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val role: String,
    val teamName: String,
    val teamId: Long,
    val photo: String = "https://mdbootstrap.com/img/new/avatars/${(1..25).random()}.jpg"
)