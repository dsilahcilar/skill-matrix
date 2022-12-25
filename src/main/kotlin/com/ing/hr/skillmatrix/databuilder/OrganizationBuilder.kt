package com.ing.hr.skillmatrix.databuilder

import com.ing.hr.skillmatrix.dto.Organization
import com.ing.hr.skillmatrix.service.OrganizationService
import org.springframework.stereotype.Service

@Service
class OrganizationBuilder(
    private val organizationService: OrganizationService
) {
    var organization: Organization? = null

    fun addEmployee(employeeID: Long, organizationID: Long): OrganizationBuilder {
        organizationService.addEmployee(employeeID, organizationID)
        return this
    }

    fun addOrganization(organization: Organization): OrganizationBuilder {
        this.organization = organizationService.addOrganization(organization)
        return this
    }

    fun addSubOrganization(parentId: Long, organization: Organization): OrganizationBuilder {
        this.organization = organizationService.addSubOrganization(parentId, organization)
        return this
    }

    fun getOrganization(id: Long): OrganizationBuilder {
        this.organization = organizationService.getOrganization(id)
        return this
    }

    fun getOrganizations(): List<Organization> {
        return organizationService.getOrganizations()
    }

}
