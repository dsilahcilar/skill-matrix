package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.data.SkillEntity
import com.ing.hr.skillmatrix.data.SkillRepository
import com.ing.hr.skillmatrix.dto.Skill
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

