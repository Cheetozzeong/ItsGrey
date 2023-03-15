package com.tntt.book.datasource

import com.tntt.book.model.datasource.BookDto

interface RemoteBookDataSource{
    fun createBook(book: Book)
    fun getPages(bookId: String): List<String>
    fun getFirstPage(bookId: String): String
    fun deleteBookById(bookId: String)
    fun getBookById(bookId: String): Book?
    fun updateBook(book: Book): Book
}