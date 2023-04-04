package com.tntt.home.usecase

import android.util.Log
import com.tntt.home.model.Book
import com.tntt.model.*
import com.tntt.repo.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
) {
    suspend fun createBook(userId: String, bookInfo: BookInfo): Flow<Book> = flow {
        bookRepository.createBookInfo(userId, bookInfo).collect() { resultBookInfo ->
            val imageBoxInfoList = mutableListOf<ImageBoxInfo>()
            val textBoxInfoList = mutableListOf<TextBoxInfo>()
            emit(Book(resultBookInfo, Thumbnail(imageBoxInfoList, textBoxInfoList)))
        }
    }

    suspend fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<Book>> = flow {
        val bookList = mutableListOf<Book>()
        bookRepository.getBookInfoList(userId, sortType, startIndex, bookType).collect() { bookInfoList ->
            for (bookInfo in bookInfoList) {
                pageRepository.getFirstPageInfo(bookInfo.id).collect() { pageInfo ->
                    if(pageInfo != null) {
                        pageRepository.getThumbnail(pageInfo.id).collect() { thumbnail ->
                            bookList.add(Book(bookInfo, thumbnail))
                        }
                    }
                    else {
                        val imageBoxList = mutableListOf<ImageBoxInfo>()
                        val textBoxList = mutableListOf<TextBoxInfo>()
                        bookList.add(Book(bookInfo, Thumbnail(imageBoxList, textBoxList)))
                    }
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