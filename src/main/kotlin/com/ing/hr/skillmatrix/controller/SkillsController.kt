package com.ing.hr.skillmatrix.controller

import com.ing.hr.skillmatrix.dto.Skill
import com.ing.hr.skillmatrix.service.SkillService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/skills")
class SkillsController(val skillService: SkillService) {

    @GetMapping
    fun getSkills(): List<Skill> {
        return skillService.getSkills()
    }

    @PostMapping
    fun addOrganization(@RequestBody skill: Skill) {
        skillService.add(skill.name)
    }
}

