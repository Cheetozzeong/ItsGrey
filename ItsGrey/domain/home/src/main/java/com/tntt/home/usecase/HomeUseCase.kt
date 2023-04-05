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
        bookRepository.createBookInfo(userId, bookInfo).collect() { bookId ->
            if(bookId == bookInfo.id) {
                val imageBoxInfoList = mutableListOf<ImageBoxInfo>()
                val textBoxInfoList = mutableListOf<TextBoxInfo>()
                emit(Book(bookInfo, Thumbnail(imageBoxInfoList, textBoxInfoList)))
            }
        }
    }

    suspend fun getBooks(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<Book>> = flow {
        val bookList = mutableListOf<Book>()
        bookRepository.getBookInfoList(userId, sortType, startIndex, bookType).collect() { bookInfoList ->
            Log.d("haha", "getBooks bookInfoList = ${bookInfoList}")
            for (bookInfo in bookInfoList) {
                pageRepository.getFirstPageInfo(bookInfo.id).collect() { firstPage ->
                    Log.d("haha", "getBooks pageInfo = ${firstPage}")
                    if(firstPage != null) {
                        pageRepository.getThumbnail(firstPage.id).collect() { thumbnail ->
                            Log.d("haha", "getBooks thumbnail = ${thumbnail}")
                            bookList.add(Book(bookInfo, thumbnail))
                        }
                    }
                    else {
                        Log.d("haha", "firstPage of ${bookInfo.id} is null")
                        val imageBoxList = mutableListOf<ImageBoxInfo>()
                        val textBoxList = mutableListOf<TextBoxInfo>()
                        bookList.add(Book(bookInfo, Thumbnail(imageBoxList, textBoxList)))
                    }
                }
            }
            emit(bookList)
        }
    }

    suspend fun deleteBookList(bookIdList: List<String>): Flow<Boolean> = flow {
        bookRepository.deleteBookInfoList(bookIdList).collect() { result ->
            emit(result)
        }
    }
}