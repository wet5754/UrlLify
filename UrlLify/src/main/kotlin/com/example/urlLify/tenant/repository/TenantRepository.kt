package com.example.urlLify.tenant.repository

import com.example.urlLify.entity.mongo.Tenant
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : MongoRepository<Tenant, String> {
    fun findByName(name: String): Tenant?
}
