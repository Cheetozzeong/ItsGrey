package com.tntt.book.repository

import android.util.Log
import com.tntt.book.datasource.RemoteBookDataSource
import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.model.BookInfo
import com.tntt.repo.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDataSource: RemoteBookDataSource
) : BookRepository {

    override suspend fun createBookInfo(userId: String, bookInfo: BookInfo): Flow<String> = flow {
        val bookDto = BookDto(bookInfo.id, userId, bookInfo.title, BookType.WORKING, Date())
        bookDataSource.createBookDto(userId, bookDto).collect() { bookId ->
            emit(bookId)
        }
    }

    override suspend fun getBookInfo(bookId: String): Flow<BookInfo> = flow {
        Log.d("function test", "getBookInfo(${bookId})")
        bookDataSource.getBookDto(bookId).collect() { bookDto ->
            Log.d("function test", "bookRepositoryImpl bookDto = ${bookDto}")
            val id = bookDto.id
            val title = bookDto.title
            val saveDate = bookDto.saveDate
            emit(BookInfo(id, title, saveDate))
        }
    }

    override suspend fun getBookInfoList(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<BookInfo>> = flow {
        bookDataSource.getBookDtoList(userId, sortType, startIndex, bookType).collect() { bookDtoList ->
            val bookInfoList = mutableListOf<BookInfo>()
            for (bookDto in bookDtoList){
                bookInfoList.add(BookInfo(bookDto.id, bookDto.title, bookDto.saveDate))
            }
            emit(bookInfoList)
        }
    }

    override suspend fun updateBookInfo(bookInfo: BookInfo, userId: String, bookType: BookType): Flow<Boolean> = flow {
        bookDataSource.updateBookDto(BookDto(bookInfo.id, userId, bookInfo.title, bookType, bookInfo.saveDate)).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteBookInfoList(bookIdList: List<String>): Flow<Boolean> = flow {
        bookDataSource.deleteBookDto(bookIdList).collect() { result ->
            emit(result)
        }
    }
}