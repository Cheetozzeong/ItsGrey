package com.tntt.home.usecase

import com.tntt.home.model.Book
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.repo.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
) {
    suspend fun createBook(userId: String): Flow<Book> = flow {
        bookRepository.createBookInfo(userId).collect() { bookId ->
            bookRepository.getBookInfo(bookId).collect() { bookInfo ->
                if (pageRepository.hasCover(bookId)) {
                    val firstPage = pageRepository.getFirstPageInfo(bookId).collect() { firstPage ->
                        pageRepository.getThumbnail(firstPage.id).collect() { thumbnail ->
                            emit(Book(bookInfo, thumbnail))
                        }
                    }
                }
                else
                    emit(Book(bookInfo, null))

            }
        }
    }

    fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<Book>> = flow {
        val bookList = mutableListOf<Book>()
        bookRepository.getBookInfos(userId, sortType, startIndex, bookType).collect() { bookInfoList ->
            for (bookInfo in bookInfoList) {
                pageRepository.getFirstPageInfo(bookInfo.id).collect() { firstPage ->
                    pageRepository.getThumbnail(firstPage.id).collect() { thumbnail ->
                        bookList.add(Book(bookInfo, thumbnail))
                    }
                }
            }
            emit(bookList)
        }
    }

    fun deleteBook(bookIdList: List<String>): Flow<Boolean> = flow {
        bookRepository.deleteBookInfo(bookIdList).collect() { result ->
            emit(result)
        }
    }
}