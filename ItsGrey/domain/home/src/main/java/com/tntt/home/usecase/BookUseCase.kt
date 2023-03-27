package com.tntt.home.usecase

import com.tntt.home.model.Book
import com.tntt.model.BookInfo
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.model.Thumbnail
import com.tntt.repo.*
import javax.inject.Inject

class BookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
    private val imageBoxRepository: ImageBoxRepository,
    private val textBoxRepository: TextBoxRepository,
    private val layerRepository: LayerRepository,
) {
    fun createBook(userId: String): Book {
        val bookId = bookRepository.createBook(userId)

        val bookInfo = bookRepository.getBookInfo(bookId)
        val firstPage = pageRepository.getFirstPageInfo(bookId)
        val imageBox = imageBoxRepository.getImageBoxInfo(firstPage.id)
        val textBoxInfoList = textBoxRepository.getTextBoxInfoList(firstPage.id)
        val sumLayer = layerRepository.getSumLayer(firstPage.id)
        return Book(bookInfo, Thumbnail(imageBox, sumLayer, textBoxInfoList))
    }

    fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): List<Book> {
        val bookList = mutableListOf<Book>()

        val bookInfoList = bookRepository.getBookInfos(userId, sortType, startIndex, bookType)
        for (bookInfo in bookInfoList) {
            val firstPage = pageRepository.getFirstPageInfo(bookInfo.id)
            val imageBox = imageBoxRepository.getImageBoxInfo(firstPage.id)
            val textBoxInfoList = textBoxRepository.getTextBoxInfoList(firstPage.id)
            val sumLayer = layerRepository.getSumLayer(firstPage.id)
            bookList.add(Book(bookInfo, Thumbnail(imageBox, sumLayer, textBoxInfoList)))
        }
        return bookList
    }

    fun deleteBook(bookIdList: List<String>): Boolean {
        return bookRepository.deleteBook(bookIdList)
    }
}