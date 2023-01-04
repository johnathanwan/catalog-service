package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.config.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles

@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val jdbcAggregateTemplate: JdbcAggregateTemplate,
) {
    @Test
    fun `find book by isbn when existing`() {
        val bookIsbn = "1234561237"
        val book = Book(bookIsbn, "Title", "Author", 12.90)
        jdbcAggregateTemplate.insert(book)
        val actualBook = bookRepository.findByIsbn(bookIsbn)

        assertThat(actualBook).isNotNull
        assertThat(actualBook?.isbn).isEqualTo(book.isbn)
    }

}