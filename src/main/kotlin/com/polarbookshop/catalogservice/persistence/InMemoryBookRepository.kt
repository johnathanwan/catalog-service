package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.*
import org.springframework.stereotype.*
import java.util.concurrent.*

@Repository
class InMemoryBookRepository : BookRepository {
    private val books: MutableMap<String, Book> = ConcurrentHashMap()
    override fun findAll(): Iterable<Book> = books.values

    override fun findByIsbn(isbn: String): Book? = if (existsByIsbn(isbn)) books[isbn] else null

    override fun existsByIsbn(isbn: String): Boolean = books[isbn] != null

    override fun save(book: Book): Book {
        books[book.isbn] = book
        return book
    }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}
