package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("books")
class BookController(val bookService: BookService) {

    @GetMapping fun get(): Iterable<Book> = bookService.viewBookList()

    @GetMapping("{isbn}")
    fun getByIsbn(@PathVariable isbn: String): Book = bookService.viewBookDetails(isbn)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody book: Book): Book = bookService.addBookToCatalog(book)

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable isbn: String) = bookService.removeBookFromCatalog(isbn)

    @PutMapping("{isbn}")
    fun put(@PathVariable isbn: String, @RequestBody book: Book): Book =
        bookService.editBookDetails(isbn, book)
}
