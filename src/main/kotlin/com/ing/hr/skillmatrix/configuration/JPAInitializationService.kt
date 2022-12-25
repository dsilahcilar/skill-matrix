package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.data.*
import com.ing.hr.skillmatrix.databuilder.NameGenerator
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class JPAInitializationService(
    private val roleRepository: RoleRepository,
    private val skillRepository: SkillRepository,
    private val employeeRepository: EmployeeRepository,
    private val organizationRepository: OrganizationRepository,
    private val dtoInitilizationService: DTOInitilizationService,
    private val nameGenerator: NameGenerator
) {

    //  @PostConstruct
    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun initialize() {
        // Create roles
        val ops = roleRepository.save(RoleEntity(name = "Ops Engineer"))
        val dev = roleRepository.save(RoleEntity(name = "Dev Engineer"))
        val cl = roleRepository.save(RoleEntity(name = "Chapter Lead"))


        // Create skills
        val java = skillRepository.save(SkillEntity(name = "Java"))
        val kotlin = skillRepository.save(SkillEntity(name = "Kotlin"))
        val springBoot = skillRepository.save(SkillEntity(name = "Spring Boot"))
        val docker = skillRepository.save(SkillEntity(name = "Docker"))
        val ansible = skillRepository.save(SkillEntity(name = "Ansible"))
        val aws = skillRepository.save(SkillEntity(name = "AWS"))

        // Assign skills to roles
        ops.addSkills(docker, ansible, aws)
        dev.addSkills(java, kotlin, springBoot, docker)

        roleRepository.save(ops)
        roleRepository.save(dev)
        roleRepository.save(cl)

        // Create employees and assign them roles and skills
        val employee1 = employeeRepository.save(
            buildEmployee(nameGenerator.generateRandomName(), nameGenerator.generateRandomSurname(), ops)
        ).addSkills(
            EmployeeSkillEntity(skill = docker, level = 3),
            EmployeeSkillEntity(skill = ansible, level = 2),
            EmployeeSkillEntity(skill = aws, level = 1),
        )


        val employee2 = employeeRepository.save(
            buildEmployee(nameGenerator.generateRandomName(), nameGenerator.generateRandomSurname(), dev).addSkills(
                EmployeeSkillEntity(skill = kotlin, level = 3),
                EmployeeSkillEntity(skill = java, level = 2),
                EmployeeSkillEntity(skill = docker, level = 1),
                EmployeeSkillEntity(skill = springBoot, level = 3),
            )
        )

        val ING = OrganizationEntity("ING")

        val WB = OrganizationEntity("Wholesale Banking")
            .addEmployees(employee1, employee2)
        ING.addSubOrganization(WB)

        organizationRepository.save(ING)

        dtoInitilizationService.addOrganizations().addEmployess().addRoles()

    }

    fun buildEmployee(name: String, lastName: String, role: RoleEntity) = EmployeeEntity(
        firstName = name,
        lastName = lastName,
        email = "${name}.${lastName}@ing.com",
    ).role(role)


}

