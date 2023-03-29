package com.tntt.viewer.usecase

import com.tntt.repo.BookRepository
import com.tntt.repo.PageRepository
import com.tntt.viewer.model.BookForView
import com.tntt.viewer.model.ViewPage
import javax.inject.Inject

class ViewerUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
){
//    fun getBook(bookId: String): BookForView {
//        val pageInfoList = pageRepository.getPageInfoList(bookId)
//
//        val viewPageList = mutableListOf<ViewPage>()
//        for (pageInfo in pageInfoList) {
//            viewPageList.add(ViewPage(pageInfo.order, pageRepository.getThumbnail(pageInfo.id)))
//        }
//
//        val bookInfo = bookRepository.getBookInfo(bookId)
//        return BookForView(bookInfo.title, viewPageList)
//    }
}