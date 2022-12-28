package com.polarbookshop.catalogservice.domain

import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class BookValidationTests {

    companion object {
        @JvmStatic
        private lateinit var validator: Validator
        @JvmStatic
        @BeforeAll
        fun setUp() {
            val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }

    @Test
    fun `when all fields correct then validation succeeds`() {
        val book = Book("1234567890", "Title", "Author", 9.90)
        val violations = validator.validate(book)
        assertThat(violations).isEmpty()
    }

    @Test
    fun `when isbn defined but incorrect then validation fails`() {
        val book = Book("a234567890", "Title", "Author", 9.90)
        val validations = validator.validate(book)
        assertThat(validations).hasSize(1)
        assertThat(validations.iterator().next().message)
            .isEqualTo("The ISBN format must be valid.")
    }
}
