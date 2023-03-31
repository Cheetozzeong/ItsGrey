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
    private val imageBoxRepository: ImageBoxRepository,
    private val textBoxRepository: TextBoxRepository,
){

    suspend fun createPage(bookId: String, page: Page): Flow<Page> = flow {
        pageRepository.createPageInfo(bookId, page.pageInfo).collect() { pageInfo ->
            val imageBoxInfoList = mutableListOf<ImageBoxInfo>()
            val textBoxInfoList = mutableListOf<TextBoxInfo>()
            for (imageBoxInfo in page.thumbnail.imageBoxList){
                imageBoxRepository.createImageBoxInfo(pageInfo.id, imageBoxInfo).collect() { imageBoxInfo ->

                }
            }
            for (textBoxInfo in page.thumbnail.textBoxList) {
                textBoxRepository.createTextBoxInfo(pageInfo.id, textBoxInfo).collect() { textBoxInfo ->

                }
            }
            emit(page)
        }

            /*
            백엔드에서 템플릿 처리할 경우 사용할 코드
            if(pageTemplate == PageTemplate.IMAGE || pageTemplate == PageTemplate.BOTH)
                imageBoxRepository.createImageBoxInfo(pageInfo.id, pageTemplate.imageBoxInfo!!)
            if(pageTemplate == PageTemplate.TEXT || pageTemplate == PageTemplate.BOTH) {
                textBoxRepository.createTextBoxInfo(pageInfo.id, pageTemplate.textBoxInfo!!).collect() { textBoxInfo ->
                    textBoxInfoList.add(textBoxInfo)
                }
            }
            emit(Page(pageInfo, Thumbnail(pageTemplate.imageBoxInfo, image, textBoxInfoList)))*/
        }
    }

    suspend fun getBook(bookId: String): Flow<Book> = flow {
//        bookRepository.getBookInfo(bookId).collect() { bookInfo ->
//            pageRepository.getPageInfoList(bookId).collect() { pageInfoList ->
//                Log.d("function test", "pageRepository.getPageInfoList : ${pageInfoList}")
//                val pages = mutableListOf<Page>()
//                for (pageInfo in pageInfoList) {
//                    pageRepository.getThumbnail(pageInfo.id).collect() { thumbnail ->
//                        Log.d("function test", "pageInfo : ${pageInfo}, thumbnail : ${thumbnail}")
//                        pages.add(Page(pageInfo, thumbnail))
//                    }
//                }
//                emit(Book(bookInfo, pages))
//            }
//        }
    }

    suspend fun savePages(bookId: String, pages: List<Page>): Flow<Boolean> = flow {
//        val pageInfoList = mutableListOf<PageInfo>()
//        for (page in pages) {
//            pageInfoList.add(page.pageInfo)
//        }
//        pageRepository.updatePageInfoList(bookId, pageInfoList).collect() { result ->
//            emit(result)
//        }
    }

    suspend fun saveBook(book: Book, userId: String, bookType: BookType = BookType.WORKING): Flow<Boolean> = flow {
//        savePages(book.bookInfo.id, book.pages).collect() { savePagesResult ->
//            bookRepository.updateBookInfo(book.bookInfo, userId, bookType).collect() { updateBookInfoResult ->
//                emit(savePagesResult && updateBookInfoResult)
//            }
//        }

    }

    suspend fun publishBook(book: Book, userId: String): Flow<Boolean> = flow {
//        pageRepository.hasCover(book.bookInfo.id).collect() { hasCover ->
//            if (hasCover) {
//                saveBook(book, userId, BookType.PUBLISHED).collect() { result ->
//                    Log.d("function test", "book has cover..")
//                    emit(result)
//                }
//            }
//            else{
//                Log.d("function test", "book has no cover..")
//            }
//        }
//    }
}