package com.tntt.book.usecase

import com.tntt.book.model.Book
import com.tntt.book.model.BookForPublication
import com.tntt.book.model.BookForWrite
import com.tntt.book.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchBookList @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(): Flow<List<Book>> {
        return bookRepository.getBooksForList()
    }
}

class FetchBooksForPublication @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(type: String): Flow<List<BookForPublication>> {
        return bookRepository.getBooksForPublication(type)
    }
}

class FetchBooksForWrite @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(type: String): Flow<List<BookForWrite>> {
        return bookRepository.getBooksForWrite(type)
    }
}