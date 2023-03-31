package com.tntt.repo

import com.tntt.model.*
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookInfo(bookId: String): Flow<BookInfo>
    suspend fun getBookInfoList(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<BookInfo>>
    suspend fun createBookInfo(userId: String, bookInfo: BookInfo): Flow<BookInfo>
    suspend fun updateBookInfo(bookInfo: BookInfo, userId: String, bookType: BookType): Flow<Boolean>
    suspend fun deleteBookInfo(bookIdList: List<String>): Flow<Boolean>
}