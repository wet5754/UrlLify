package com.example.urlLify.tenant.service

import com.example.urlLify.entity.mongo.Tenant
import com.example.urlLify.tenant.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class TenantService(private val tenantRepository: ProductRepository) {
    fun findAll(): List<Tenant> = tenantRepository.findAll()

    fun findByName(name: String): Tenant? = tenantRepository.findByName(name)
}
