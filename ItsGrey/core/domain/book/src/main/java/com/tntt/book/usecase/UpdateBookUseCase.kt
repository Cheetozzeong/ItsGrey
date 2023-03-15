package com.tntt.book.usecase

import com.tntt.book.model.Book
import com.tntt.book.repository.BookRepository
import javax.inject.Inject

class UpdateBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(book: Book) {
        return bookRepository.updateBook(book)
    }
}
