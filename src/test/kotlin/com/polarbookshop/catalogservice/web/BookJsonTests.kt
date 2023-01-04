package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.time.*

@JsonTest
class BookJsonTests(@Autowired private val json: JacksonTester<Book>) {

    @Test
    fun `test serialize`() {
        val now = Instant.now()
        val book = Book("1234567890", "Title", "Author", 9.90, now, now, 21, 394L)
        val jsonContent = json.write(book)

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn)
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title)
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author)
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price)
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate").isEqualTo(book.createdDate?.toString())
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate").isEqualTo(book.lastModifiedDate?.toString())
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(book.version)
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(book.id?.toInt())

    }

    @Test
    fun `test deserialize`() {
        val instant = Instant.parse("2023-01-04T13:35:37.135029Z")
        val content = """
            {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90,
                "createdDate": "2023-01-04T13:35:37.135029Z",
                "lastModifiedDate": "2023-01-04T13:35:37.135029Z",
                "version": 21,
                "id": 394
            }
             """.trimIndent()

        assertThat(json.parse(content)).usingRecursiveComparison()
            .isEqualTo(Book("1234567890", "Title", "Author", 9.90, instant,instant, 21, 394L))
    }
}
