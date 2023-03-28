package com.tntt.book.repository

import android.util.Log
import com.tntt.book.datasource.RemoteBookDataSource
import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.model.BookInfo
import com.tntt.repo.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDataSource: RemoteBookDataSource
) : BookRepository {

    override suspend fun getBookInfo(bookId: String): Flow<BookInfo> = flow {
        bookDataSource.getBookDto(bookId).collect() { bookDto ->
            val id = bookDto.id
            val title = bookDto.title
            val saveDate = bookDto.saveDate
            emit(BookInfo(id, title, saveDate))
        }
    }

    override suspend fun getBookInfos(
        userId: String,
        sortType: SortType,
        startIndex: Long,
        bookType: BookType
    ): Flow<List<BookInfo>> = flow {
        bookDataSource.getBookDtos(userId, sortType, startIndex, bookType).collect() { bookDtoList ->
            val bookList = mutableListOf<BookInfo>()
            for (bookDto in bookDtoList){
                bookList.add(BookInfo(bookDto.id, bookDto.title, bookDto.saveDate))
            }
            emit(bookList)
        }
    }

    override suspend fun createBookInfo(userId: String): Flow<String> = flow {
        bookDataSource.createBookDto(userId).collect() { bookDtoId ->
            emit(bookDtoId)
        }
    }

    override suspend fun updateBookInfo(bookInfo: BookInfo, userId: String, bookType: BookType): Flow<Boolean> = flow {
        bookDataSource.updateBookDto(BookDto(bookInfo.id, userId, bookInfo.title, bookType, bookInfo.saveDate)).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteBookInfo(bookIdList: List<String>): Flow<Boolean> = flow {
        bookDataSource.deleteBook(bookIdList).collect() { result ->
            emit(result)
        }
    }
}