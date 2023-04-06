package com.tntt.repo

import com.tntt.model.*
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun createBookInfo(userId: String, bookInfo: BookInfo): Flow<String>
    suspend fun getBookInfo(bookId: String): Flow<BookInfo>
    suspend fun getBookInfoList(userId: String, sortType: SortType, startIndex: Int, bookType: BookType): Flow<List<BookInfo>>
    suspend fun updateBookInfo(bookInfo: BookInfo, userId: String, bookType: BookType): Flow<Boolean>
    suspend fun deleteBookInfoList(bookIdList: List<String>): Flow<Boolean>
}