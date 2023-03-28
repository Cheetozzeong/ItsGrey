package com.tntt.editbook.usecase

import com.tntt.editbook.model.Book
import com.tntt.editbook.model.Page
import com.tntt.model.BookType
import com.tntt.model.PageInfo
import com.tntt.repo.BookRepository
import com.tntt.repo.PageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
){

    fun createPage() {
        TODO("템플릿 받아서 페이지 만들기")
    }

    fun getBook(bookId: String): Flow<Book> = flow {
        val bookInfo = bookRepository.getBookInfo(bookId).collect() { bookInfo ->
            pageRepository.getPageInfoList(bookId).collect() { pageInfoList ->
                val pages = mutableListOf<Page>()
                for (pageInfo in pageInfoList) {
                    pageRepository.getThumbnail(pageInfo.id).collect() { thumbnail ->
                        pages.add(Page(pageInfo, thumbnail))
                    }
                }
                emit(Book(bookInfo, pages))
            }
        }
    }

    fun savePages(bookId: String, pages: List<Page>): Flow<Boolean> = flow {
        val pageInfoList = mutableListOf<PageInfo>()
        for (page in pages) {
            pageInfoList.add(page.pageInfo)
        }
        pageRepository.updatePageInfoList(bookId, pageInfoList).collect() { result ->
            emit(result)
        }
    }

    fun saveBook(book: Book, userId: String, bookType: BookType = BookType.EDIT): Flow<Boolean> = flow {
        savePages(book.bookInfo.id, book.pages).collect() { savePagesResult ->
            bookRepository.updateBookInfo(book.bookInfo, userId, bookType).collect() { updateBookInfoResult ->
                emit(savePagesResult && updateBookInfoResult)
            }
        }

    }

    fun publishBook(book: Book, userId: String): Flow<Boolean> = flow {
        if (!pageRepository.hasCover(book.bookInfo.id))   emit(false)
        else {
            saveBook(book, userId, BookType.PUBLISHED).collect() { result ->
                emit(result)
            }
        }
    }
}