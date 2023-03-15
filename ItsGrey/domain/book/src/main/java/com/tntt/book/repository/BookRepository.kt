package com.tntt.book.repository

import com.tntt.book.model.Book
import kotlinx.coroutines.flow.Flow


interface BookRepository {

    suspend fun createBook(book: Book)

    suspend fun getPages(bookId: String): Flow<List<String>>

    suspend fun getFirstPage(bookId: String): Flow<String>

    suspend fun deleteBookById(bookId: String)

    suspend fun getBookById(bookId: String): Flow<Book?>

    suspend fun updateBook(book: Book): Flow<Book>

}
