package com.tntt.book.datasource

import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import kotlinx.coroutines.flow.Flow

interface RemoteBookDataSource{
    suspend fun createBookDto(userId: String, bookDto: BookDto): Flow<String>
    suspend fun getBookDto(bookId: String): Flow<BookDto>
    suspend fun getBookDtoList(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<BookDto>>
    suspend fun updateBookDto(bookDto: BookDto): Flow<Boolean>
    suspend fun deleteBookDto(bookIdList: List<String>): Flow<Boolean>
}