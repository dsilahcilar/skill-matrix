package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.dto.Skill
import com.ing.hr.skillmatrix.persistence.SkillEntity
import com.ing.hr.skillmatrix.persistence.SkillRepository
import org.springframework.stereotype.Service

@Service
class SkillService(private val skillRepository: SkillRepository) {

    fun add(name: String) {
        skillRepository.save(SkillEntity(name))
    }

    fun getSkills(): List<Skill> {
        val skills = skillRepository.findAll()
        return skills.map { it.toDTO() }
    }

}

fun SkillEntity.toDTO() = Skill(this.name, this.roles.map { toRole(it) })

