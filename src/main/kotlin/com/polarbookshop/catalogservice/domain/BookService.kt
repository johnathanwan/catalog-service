package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {

    fun viewBookList(): Iterable<Book> = bookRepository.findAll()

    fun viewBookDetails(isbn: String): Book =
        bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn)) {
            throw BookAlreadyExistsException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String) = bookRepository.deleteByIsbn(isbn)

    fun editBookDetails(isbn: String, book: Book): Book =
        bookRepository.findByIsbn(isbn)?.let {
            bookRepository.save(
                Book(
                    it.isbn,
                    book.title,
                    book.author,
                    book.price,
                    book.createdDate,
                    book.lastModifiedDate,
                    book.version,
                    book.id
                )
            )
        }
            ?: addBookToCatalog(book)
}
