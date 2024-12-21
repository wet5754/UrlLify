package com.example.urlLify.url.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class ShortenRequestDto(
    @field:NotNull(message = "uri parameter required")
    @JsonProperty(required = true)
    val uri: String,
)
