package com.ing.hr.skillmatrix.controller

import com.fasterxml.jackson.annotation.JsonView
import com.ing.hr.skillmatrix.dto.*
import com.ing.hr.skillmatrix.service.ProjectService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/projects")
class ProjectController(val projectService: ProjectService) {

    @PostMapping("/{id}/projectSkills")
    @JsonView(ProjectDetailed::class)
    fun addProjectSkills(
        @PathVariable id: Long,
        @RequestBody request: List<ProjectSkillRequest>
    ): List<ProjectSkill> {
        return projectService.addProjectSkills(id, request)
    }

    @GetMapping("/{id}")
    @JsonView(ProjectDetailed::class)
    fun getProjectSkills(@PathVariable id: Long): Project? {
        return projectService.getProject(id)
    }

}
