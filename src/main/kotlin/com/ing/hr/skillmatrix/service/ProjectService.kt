package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.dto.Project
import com.ing.hr.skillmatrix.dto.ProjectSkill
import com.ing.hr.skillmatrix.dto.ProjectSkillRequest
import com.ing.hr.skillmatrix.persistence.ProjectEntity
import com.ing.hr.skillmatrix.persistence.ProjectRepository
import com.ing.hr.skillmatrix.persistence.ProjectSkillEntity
import com.ing.hr.skillmatrix.persistence.SkillRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(private val projectRepository: ProjectRepository, private val skillRepository: SkillRepository) {

    fun add(project: Project): Project {
        return projectRepository.save(ProjectEntity(project.name)).toDTO()
    }

    fun getProject(id: Long) = projectRepository.findById(id).get().toDTO()

    fun addProjectSkills(id: Long, projectSkillRequests: List<ProjectSkillRequest>): List<ProjectSkill> {
        val projectEntity = projectRepository.findById(id).get()
        projectSkillRequests.filter {
            it.level > 0
        }.forEach {
            val skillEntity = skillRepository.findById(it.skillId).get()
            projectEntity.addProjectSkill(ProjectSkillEntity(skillEntity, it.level))
        }
        return projectRepository.save(projectEntity).toDTO().projectSkills
    }

}

fun ProjectEntity.toDTO(): Project {
    return Project(this.id, this.name, this.projectSkills.map { it.toDTO() })
}

fun ProjectSkillEntity.toDTO(): ProjectSkill {
    return ProjectSkill(this.skill.toDTO(), this.level)
}