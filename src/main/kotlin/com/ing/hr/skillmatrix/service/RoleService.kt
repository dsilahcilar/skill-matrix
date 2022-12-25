package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.data.RoleEntity
import com.ing.hr.skillmatrix.data.RoleRepository
import com.ing.hr.skillmatrix.data.SkillEntity
import com.ing.hr.skillmatrix.dto.Role
import com.ing.hr.skillmatrix.dto.Skill
import org.springframework.stereotype.Service

@Service
class RoleService(private val roleRepository: RoleRepository) {

    fun add(name: String): Role {
        return roleRepository.save(RoleEntity(name)).toDTO()
    }

    fun getRoles(): List<Role> {
        val roles = roleRepository.findAll()
        return roles.map { it.toDTO() }
    }

    fun getRole(id: Long): Role {
        return roleRepository.findById(id).get().toDTO()
    }

}

fun RoleEntity.toDTO() =
    Role(id = this.id, name = this.name, this.skillSet.map { toSkill(it) }, this.employeeList.map { it.toDTO() })

fun toSkill(skillEntity: SkillEntity) = Skill(skillEntity.name, emptyList())

fun toRole(roleEntity: RoleEntity?) =
    Role(id = roleEntity?.id, name = roleEntity?.name, emptyList(), employee = emptyList())



