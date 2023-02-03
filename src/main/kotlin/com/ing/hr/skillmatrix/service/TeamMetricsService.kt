package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.dto.OrganizationMetric
import com.ing.hr.skillmatrix.persistence.OrganizationEntity
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class TeamMetricsService {


    fun buildOrganizationMetric(
        organizationEntity: OrganizationEntity,
        skillMap: HashMap<String, Int>
    ): OrganizationMetric {
        val numberOfRequiredSkills = skillMap.size
        val numberOfEmployees = organizationEntity.employeeList.size
        val numberOfProjects = organizationEntity.projectList.size
        val sumOfSkillLevels = calculateTotalSkillLevel(organizationEntity, skillMap)
        val numberOfMissingSkills = calculateNumberOfInSufficientSkills(organizationEntity, skillMap)

        return OrganizationMetric(
            organizationEntity.id,
            numberOfRequiredSkills,
            numberOfEmployees,
            numberOfProjects,
            sumOfSkillLevels.roundUp(),
            (sumOfSkillLevels / numberOfEmployees).roundUp(),
            numberOfMissingSkills
        )
    }

    fun Double.roundUp() = BigDecimal(this).setScale(1, RoundingMode.HALF_UP).toDouble()

    fun calculateTotalSkillLevel(organizationEntity: OrganizationEntity, skillMap: HashMap<String, Int>): Double {
        return skillMap.map {
            organizationEntity.employeeList.map { employeeEntity ->
                employeeEntity.getSkillLevel(it.key)
            }.average()
        }.sum()

    }

    fun calculateNumberOfInSufficientSkills(
        organizationEntity: OrganizationEntity,
        skillMap: HashMap<String, Int>
    ): Int {
        var countOfUnsufficientSkills = 0
        skillMap.forEach {
            val numberOfEmployeesUnderMinimumLevel =
                calculateNumberOfEmployeesUnderMinLevel(it.key, it.value, organizationEntity)
            if (numberOfEmployeesUnderMinimumLevel > 1) countOfUnsufficientSkills++
        }
        return countOfUnsufficientSkills
    }

    fun calculateNumberOfEmployeesUnderMinLevel(
        skill: String,
        level: Int,
        organizationEntity: OrganizationEntity
    ): Int {
        var countOfEmployeesUnderMinimumLevel = 0
        organizationEntity.employeeList.forEach { employeeEntity ->
            countOfEmployeesUnderMinimumLevel = 0
            val employeeLevel = employeeEntity.getSkillLevel(skill)
            if (employeeLevel < level) {
                countOfEmployeesUnderMinimumLevel++
            }
        }
        return countOfEmployeesUnderMinimumLevel
    }
}