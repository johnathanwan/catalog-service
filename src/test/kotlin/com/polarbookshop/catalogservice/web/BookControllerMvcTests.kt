package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.*
import org.springframework.http.*
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.*

@WebMvcTest(BookController::class)
class BookControllerMvcTests(
    @Autowired private val mockMvc: MockMvc,
) {

    @MockBean private lateinit var bookService: BookService

    @Test
    fun `when get book not existing then should return 404`() {
        val isbn = "73737313940"
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException::class.java)
        mockMvc.perform(get("/books/$isbn")).andExpect(status().isNotFound)
    }
}
