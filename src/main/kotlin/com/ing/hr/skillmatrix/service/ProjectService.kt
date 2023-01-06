package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.dto.Project
import com.ing.hr.skillmatrix.persistence.ProjectEntity
import com.ing.hr.skillmatrix.persistence.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(private val projectRepository: ProjectRepository) {

    fun add(project: Project): Project {
        return projectRepository.save(ProjectEntity(project.name)).toDTO()
    }

}

fun ProjectEntity.toDTO(): Project {
    return Project(this.name)
}