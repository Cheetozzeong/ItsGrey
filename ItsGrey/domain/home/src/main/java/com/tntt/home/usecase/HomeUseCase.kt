package com.tntt.home.usecase

import android.util.Log
import com.tntt.home.model.Book
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.repo.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
) {
    suspend fun createBook(userId: String, book: Book): Flow<Book> = flow {
        bookRepository.createBookInfo(userId, book.bookInfo).collect() { resultBook ->
            emit(book)
        }
    }

    suspend fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<Book>> = flow {
        val bookList = mutableListOf<Book>()
        bookRepository.getBookInfoList(userId, sortType, startIndex, bookType).collect() { bookInfoList ->
            for (bookInfo in bookInfoList) {
                pageRepository.getThumbnail(bookInfo.id).collect() { thumbnail ->
                    bookList.add(Book(bookInfo, thumbnail))
                }
            }
            emit(bookList)
        }
    }

    suspend fun deleteBook(bookIdList: List<String>): Flow<Boolean> = flow {
        bookRepository.deleteBookInfo(bookIdList).collect() { result ->
            emit(result)
        }
    }
}