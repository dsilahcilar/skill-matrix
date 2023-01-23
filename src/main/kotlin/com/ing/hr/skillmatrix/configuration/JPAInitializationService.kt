package com.ing.hr.skillmatrix.service

import com.ing.hr.skillmatrix.databuilder.ORGANIZATIONS
import com.ing.hr.skillmatrix.persistence.*
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class JPAInitializationService(
    private val roleRepository: RoleRepository,
    private val skillRepository: SkillRepository,
    private val dtoInitilizationService: DTOInitilizationService,
    private val organizationRepository: OrganizationRepository
) {

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
        cl.addSkills(docker, kotlin)

        roleRepository.save(ops)
        roleRepository.save(dev)
        roleRepository.save(cl)

        dtoInitilizationService.addOrganizations()

        buildOrganizationTree()

    }

    fun buildOrganizationTree() {
        val ING = organizationRepository.findByParentNull().first()

        val wholeSale = organizationRepository.findByName("Wholesale Banking").first()
        ING.addSubOrganization(wholeSale)
        val trade = organizationRepository.findByName("Trade").first()
        val lending = organizationRepository.findByName("Lending").first()
        wholeSale.addSubOrganization(trade).addSubOrganization(lending)
        // wholeSale END.

        val domestic = organizationRepository.findByName("Domestic Banking").first()
        ING.addSubOrganization(domestic)
        // Domestic END.

        val retail = organizationRepository.findByName("Retail Banking").first()
        ING.addSubOrganization(retail)
        // Retail END.

        val privateBanking = organizationRepository.findByName("Private Banking").first()
        ING.addSubOrganization(privateBanking)
        // privateBanking END.

        val HR = organizationRepository.findByName("Human Resources").first()
        ING.addSubOrganization(HR)
        // HR END.

        val investment = organizationRepository.findByName("Investment Banking").first()
        ING.addSubOrganization(investment)
        // investment END.


    }

    fun buildEmployee(name: String, lastName: String, role: RoleEntity) = EmployeeEntity(
        firstName = name,
        lastName = lastName,
        email = "${name}.${lastName}@ing.com",
    ).role(role)


}

