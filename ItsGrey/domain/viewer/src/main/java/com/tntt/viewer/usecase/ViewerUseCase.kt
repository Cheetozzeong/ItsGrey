package com.tntt.viewer.usecase

import com.tntt.repo.BookRepository
import com.tntt.repo.PageRepository
import com.tntt.viewer.model.BookForView
import com.tntt.viewer.model.ViewPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewerUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
){
    fun getBook(bookId: String): Flow<BookForView> = flow {
        bookRepository.getBookInfo(bookId).collect() { bookInfo ->
            pageRepository.getPageInfoList(bookId).collect() { pageInfoList ->
                val viewPageList = mutableListOf<ViewPage>()
                for (pageInfo in pageInfoList) {
                    pageRepository.getThumbnail(pageInfo.id).collect() { thumbnail ->
                        viewPageList.add(ViewPage(pageInfo.order, thumbnail))
                    }
                }
                emit(BookForView(bookInfo.title, viewPageList))
            }
        }
    }
}