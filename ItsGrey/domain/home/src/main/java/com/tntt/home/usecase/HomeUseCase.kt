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
    suspend fun createBook(userId: String, bookId: String): Flow<Book> = flow {
        bookRepository.createBookInfo(userId, bookId).collect() { bookInfo ->
            bookRepository.getBookInfo(bookInfo.id).collect() { bookInfo ->
                emit(Book(bookInfo, null))
            }
        }
    }

    suspend fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<Book>> = flow {
        Log.d("function test", "getBooks(${userId}, ${sortType}, ${startIndex}, ${bookType})")
        val bookList = mutableListOf<Book>()
        bookRepository.getBookInfoList(userId, sortType, startIndex, bookType).collect() { bookInfoList ->
            Log.d("function test", "\tbookInfoList = ${bookInfoList}")
            for (bookInfo in bookInfoList) {
                pageRepository.getCoverPageInfo(bookInfo.id).collect() { firstPage ->
                    if(firstPage == null) bookList.add(Book(bookInfo, null))
                    else {
                        pageRepository.getThumbnail(firstPage.id).collect() { thumbnail ->
                            bookList.add(Book(bookInfo, thumbnail))
                        }
                    }
                }
            }
            Log.d("function test", "emit(${bookList})")
            emit(bookList)
        }
    }

    suspend fun deleteBook(bookIdList: List<String>): Flow<Boolean> = flow {
        Log.d("function test=======================", "deleteBookList(${bookIdList})")
        bookRepository.deleteBookInfo(bookIdList).collect() { result ->
            emit(result)
        }
    }
}