package com.tntt.book.datasource

import com.tntt.book.model.datasource.BookDto

interface BookDataSource{
    fun createBook(book: BookDto): Boolean
    fun readBookById(id: String): BookDto
    fun updateBook(book: BookDto): BookDto
    fun deleteBook(id: String): Boolean
}