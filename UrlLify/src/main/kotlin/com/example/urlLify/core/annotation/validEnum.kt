package com.example.urlLify.core.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidEnumValidator::class])
annotation class ValidEnum(
    val enumClass: KClass<out Enum<*>>,
    val message: String = "Invalid value",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class ValidEnumValidator : ConstraintValidator<ValidEnum, String> {
    private lateinit var acceptedValues: Set<String>

    override fun initialize(annotation: ValidEnum) {
        acceptedValues = annotation.enumClass.java.enumConstants.map { it.name }.toSet()
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return false
        return acceptedValues.contains(value)
    }
}
