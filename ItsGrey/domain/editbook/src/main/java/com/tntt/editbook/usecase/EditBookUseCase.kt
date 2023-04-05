package com.tntt.editbook.usecase

import android.graphics.Bitmap
import android.util.Log
import com.tntt.editbook.model.Book
import com.tntt.editbook.model.Page
import com.tntt.model.*
import com.tntt.repo.BookRepository
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.PageRepository
import com.tntt.repo.TextBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val pageRepository: PageRepository,
){

    suspend fun createPage(bookId: String, pageInfo: PageInfo): Flow<Page> = flow {
        pageRepository.createPageInfo(bookId, pageInfo).collect() { pageId ->
            pageRepository.getThumbnail(pageId).collect() { thumbnail ->
                emit(Page(pageInfo, thumbnail))
            }
        }
    }

    suspend fun getBook(bookId: String): Flow<Book> = flow {
        bookRepository.getBookInfo(bookId).collect() { bookInfo ->
            pageRepository.getPageInfoList(bookId).collect() { pageInfoList ->
                Log.d("function test", "pageInfoList = ${pageInfoList}")
                val pages = mutableListOf<Page>()
                for (pageInfo in pageInfoList) {
                    Log.d("function test", "pageInfo = ${pageInfo}")
                    pageRepository.getThumbnail(pageInfo.id).collect() { thumbnail ->
                        Log.d("function test", "thumbnail = ${thumbnail}")
                        pages.add(Page(pageInfo, thumbnail))
                    }
                }
                emit(Book(bookInfo, pages))
            }
        }
    }

    suspend fun savePages(bookId: String, pages: List<Page>): Flow<Boolean> = flow {
        val pageInfoList = mutableListOf<PageInfo>()
        for (page in pages) {
            pageInfoList.add(page.pageInfo)
        }
        pageRepository.updatePageInfoList(bookId, pageInfoList).collect() { result ->
            emit(result)
        }
    }

    suspend fun saveBook(book: Book, userId: String, bookType: BookType = BookType.WORKING): Flow<Boolean> = flow {
//        savePages(book.bookInfo.id, book.pages).collect() { savePagesResult ->
//            bookRepository.updateBookInfo(book.bookInfo, userId, bookType).collect() { updateBookInfoResult ->
//                emit(savePagesResult && updateBookInfoResult)
//            }
//        }

    }

    suspend fun publishBook(book: Book, userId: String): Flow<Boolean> = flow {
        pageRepository.hasCover(book.bookInfo.id).collect() { hasCover ->
            if (hasCover) {
                saveBook(book, userId, BookType.PUBLISHED).collect() { result ->
                    emit(result)
                }
            }
            else{
                emit(false)
            }
        }
    }

    suspend fun deletePage(pageId: String): Flow<Boolean> = flow {
        pageRepository.deletePageInfo(pageId).collect() { result ->
            emit(result)
        }
    }
}