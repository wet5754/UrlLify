package com.example.urlLify.tenant.dto

import com.example.urlLify.entity.mongo.Tenant

data class GetAllTenantsResponseDto(
    val tenants: List<Tenant>,
    val total: Int
)
