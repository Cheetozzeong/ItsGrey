package com.tntt.book.repository

import com.tntt.book.model.Book
import com.tntt.book.model.BookForPublication
import com.tntt.book.model.BookForWrite
import kotlinx.coroutines.flow.Flow


interface BookRepository {

    suspend fun postBook(book: Book)

    suspend fun getBooksForList(): Flow<List<Book>>

    suspend fun getBooksForPublication(type: String): Flow<List<BookForPublication>>

    suspend fun getBooksForWrite(type: String): Flow<List<BookForWrite>>

    suspend fun deleteBookById(bookId: Int)

    suspend fun getBookById(bookId: Int): Book?

    suspend fun updateBook(book: Book)
}
