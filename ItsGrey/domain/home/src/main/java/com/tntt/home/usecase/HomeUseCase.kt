package com.tntt.home.usecase

import com.tntt.home.model.Book
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.repo.*
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
) {
    suspend fun createBook(userId: String): Book {
        val bookId = bookRepository.createBookInfo(userId)
        val bookInfo = bookRepository.getBookInfo(bookId)

        if (pageRepository.hasCover(bookId)) {
            val firstPage = pageRepository.getFirstPageInfo(bookId)
            return Book(bookInfo, pageRepository.getThumbnail(firstPage.id))
        }
        else
            return Book(bookInfo, null)
    }

    fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): List<Book> {
        val bookList = mutableListOf<Book>()
        val bookInfoList = bookRepository.getBookInfos(userId, sortType, startIndex, bookType)
        for (bookInfo in bookInfoList) {
            val firstPage = pageRepository.getFirstPageInfo(bookInfo.id)
            bookList.add(Book(bookInfo, pageRepository.getThumbnail(firstPage.id)))
        }
        return bookList
    }

    fun deleteBook(bookIdList: List<String>): Boolean {
        return bookRepository.deleteBookInfo(bookIdList)
    }
}