package com.tntt.book.repository

import com.tntt.book.model.BookInfo
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun createBook(book: BookInfo): Flow<String>

    suspend fun getPagesIds(bookId: String): Flow<List<String>>

    suspend fun getFirstPageId(bookId: String): Flow<String>

    suspend fun deleteBookById(bookId: String)

    suspend fun findBookById(bookId: String): Flow<BookInfo?>

    suspend fun updateBook(book: BookInfo): Flow<String>

}
