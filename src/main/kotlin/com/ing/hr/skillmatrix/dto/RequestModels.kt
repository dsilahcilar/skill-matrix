package com.ing.hr.skillmatrix.dto

data class ProjectSkillRequest(
    val skillId: Long,
    val minLevel: Int
)

data class EmployeeSkillRequest(
    val skillId: Long,
    val level: Int
)