package com.ing.hr.skillmatrix.persistence

import jakarta.persistence.*

@Entity
@Table(name = "organization")
data class OrganizationEntity(
    val name: String,

    ) {
    @Id
    @GeneratedValue
    var id: Long? = null
        private set

    @OneToMany(cascade = [CascadeType.ALL])
    var subOrganizations: MutableList<OrganizationEntity> = mutableListOf()
        private set

    @ManyToOne(cascade = [CascadeType.ALL])
    var parent: OrganizationEntity? = null
        private set

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "organization")
    var employeeList: MutableList<EmployeeEntity> = mutableListOf()
        private set

    @OneToMany(cascade = [CascadeType.ALL])
    var projectList: MutableList<ProjectEntity> = mutableListOf()
        private set

    fun addEmployee(employee: EmployeeEntity): OrganizationEntity {
        employee.organization = this
        employeeList.add(employee)
        return this
    }

    fun addEmployees(vararg employees: EmployeeEntity): OrganizationEntity {
        employees.forEach { addEmployee(it) }
        return this
    }

    fun addProject(project: ProjectEntity): OrganizationEntity {
        project.organization = this
        projectList.add(project)
        return this
    }

    fun addProjects(vararg projects: ProjectEntity): OrganizationEntity {
        projects.forEach { addProject(it) }
        return this
    }

    fun addSubOrganization(sub: OrganizationEntity): OrganizationEntity {
        sub.parent = this
        subOrganizations.add(sub)
        return this
    }

}

@Entity
@Table(name = "employee")
data class EmployeeEntity(
    val firstName: String,
    val lastName: String,
    val email: String
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @OneToMany(cascade = [CascadeType.ALL])
    var skills: MutableList<EmployeeSkillEntity> = mutableListOf()
        private set

    @ManyToOne
    var organization: OrganizationEntity? = null

    @ManyToOne
    var role: RoleEntity? = null
        private set

    fun role(roleEntity: RoleEntity): EmployeeEntity {
        roleEntity.addEmployee(this)
        this.role = roleEntity
        return this
    }

    fun addSkill(skill: EmployeeSkillEntity) {
        skill.employee = this
        skills.add(skill)
    }

    fun addSkills(vararg newSkills: EmployeeSkillEntity): EmployeeEntity {
        newSkills.forEach { this.addSkill(it) }
        return this
    }

    fun deleteExistingSkills(): EmployeeEntity {
        this.skills = mutableListOf()
        return this
    }

}

@Entity
@Table(name = "role")
data class RoleEntity(
    val name: String,
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToMany
    var skillSet: MutableList<SkillEntity> = mutableListOf()
        private set

    @OneToMany
    var employeeList: MutableList<EmployeeEntity> = mutableListOf()
        private set

    fun addSkill(skill: SkillEntity): RoleEntity {
        skillSet.add(skill)
        return this
    }

    fun addSkills(vararg skills: SkillEntity): RoleEntity {
        skills.forEach { addSkill(it) }
        return this
    }

    fun addEmployee(employee: EmployeeEntity): RoleEntity {
        employeeList.add(employee)
        return this
    }
}

@Entity
@Table(name = "skill")
data class SkillEntity(
    val name: String,
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToMany
    var roles: MutableList<RoleEntity> = mutableListOf()
}

@Entity
@Table(name = "employee_skill")
data class EmployeeSkillEntity(
    @Id @GeneratedValue val id: Long? = null,
    @OneToOne
    val skill: SkillEntity,
    val level: Int
) {
    @ManyToOne
    var employee: EmployeeEntity? = null
}

@Entity
@Table(name = "project")
data class ProjectEntity(

    val name: String
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToOne
    var organization: OrganizationEntity? = null

    @OneToMany(cascade = [CascadeType.ALL])
    var projectSkills: MutableList<ProjectSkillEntity> = mutableListOf()

    fun addProjectSkill(projectSkillEntity: ProjectSkillEntity) {
        projectSkillEntity.project = this
        this.projectSkills.add(projectSkillEntity)
    }
}

@Entity
@Table(name = "project_skill")
data class ProjectSkillEntity(
    @OneToOne
    var skill: SkillEntity,
    val level: Int
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @OneToOne
    var project: ProjectEntity? = null
}

