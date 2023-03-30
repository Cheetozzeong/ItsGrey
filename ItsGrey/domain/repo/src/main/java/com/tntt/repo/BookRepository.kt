package com.tntt.repo

import com.tntt.model.BookInfo
import com.tntt.model.BookType
import com.tntt.model.SortType
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookInfo(bookId: String): Flow<BookInfo>
    suspend fun getBookInfoList(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<BookInfo>>
    suspend fun createBookInfo(userId: String, bookId: String): Flow<BookInfo>
    suspend fun updateBookInfo(bookInfo: BookInfo, userId: String, bookType: BookType): Flow<Boolean>
    suspend fun deleteBookInfo(bookIdList: List<String>): Flow<Boolean>

}