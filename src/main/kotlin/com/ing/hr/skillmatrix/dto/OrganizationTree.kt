package com.ing.hr.skillmatrix.dto

data class OrganizationTree(
    val id: Long,
    val name: String,
    val subOrganizations: List<OrganizationTree>
) {

}