package com.example.urlLify.tenant.controller

import com.example.urlLify.tenant.dto.GetAllTenantsResponseDto
import com.example.urlLify.tenant.service.TenantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["/tenants"])
@RestController
class TenantController(
    private val tenantService: TenantService,
) {
    @GetMapping(value = [""])
    fun getAllTenants(): GetAllTenantsResponseDto {
        val tenants = tenantService.findAll()
        val total = tenants.count()
        return GetAllTenantsResponseDto(tenants, total)
    }
}
