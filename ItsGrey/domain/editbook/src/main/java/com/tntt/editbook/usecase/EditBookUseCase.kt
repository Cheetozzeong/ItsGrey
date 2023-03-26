package com.tntt.editbook.usecase

import com.tntt.editbook.model.Book
import com.tntt.editbook.model.Page
import com.tntt.model.BookType
import com.tntt.model.PageInfo
import com.tntt.repo.BookRepository
import com.tntt.repo.PageRepository
import javax.inject.Inject

class EditBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
){

    fun createPage() {
        TODO("템플릿 받아서 페이지 만들기")
    }

    fun getBook(bookId: String): Book {
        val bookInfo = bookRepository.getBookInfo(bookId)
        val pageInfoList = pageRepository.getPageInfoList(bookId)
        val pages = mutableListOf<Page>()
        for (pageInfo in pageInfoList) {
            pages.add(Page(pageInfo, pageRepository.getThumbnail(pageInfo.id)))
        }
        return Book(bookInfo, pages)
    }

    fun savePages(bookId: String, pages: List<Page>): Boolean {
        val pageInfoList = mutableListOf<PageInfo>()
        for (page in pages) {
            pageInfoList.add(page.pageInfo)
        }
        return pageRepository.updatePageInfoList(bookId, pageInfoList)
    }

    fun saveBook(book: Book, userId: String, bookType: BookType = BookType.EDIT): Boolean {
        return bookRepository.updateBookInfo(book.bookInfo, userId, bookType)
    }

    fun publishBook(book: Book, userId: String): Boolean {
        if (!pageRepository.hasCover(book.bookInfo.id))   return false

        if(savePages(book.bookInfo.id, book.pages) && saveBook(book, userId, BookType.PUBLISHED))    return true
        return false
    }
}