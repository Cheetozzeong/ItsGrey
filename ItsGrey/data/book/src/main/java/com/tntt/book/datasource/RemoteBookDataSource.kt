package com.tntt.book.datasource

import com.tntt.book.model.datasource.BookDto
import kotlinx.coroutines.flow.Flow

interface RemoteBookDataSource{
    fun createBook(book: BookDto, userId: String): Flow<String>
    fun getPages(bookId: String): Flow<List<String>>
    fun getFirstPage(bookId: String): Flow<String>
    fun deleteBookById(bookId: String)
    fun getBookById(bookId: String): Flow<BookDto?>
    fun updateBook(book: BookDto): Flow<BookDto>
}