package com.example.urlLify.entity.mongo

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "tenant")
data class Tenant(
    @Id
    @JsonIgnore
    val id: String? = null,

    @Indexed(unique = true)
    val name: String? = null,
    val domain: String,

    @JsonIgnore
    val createdAt: Date,
)
