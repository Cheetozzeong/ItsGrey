package com.tntt.book.usecase

import com.tntt.book.repository.BookRepository
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(bookId: Int) {
        return bookRepository.deleteBookById(bookId)
    }
}