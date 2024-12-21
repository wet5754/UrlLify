package com.example.urlLify.core.response

data class ErrorResponse(
    val message: String,
    val errorType: String = "Invalid Argument"
)
