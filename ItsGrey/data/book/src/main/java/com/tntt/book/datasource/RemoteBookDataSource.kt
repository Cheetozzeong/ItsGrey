package com.tntt.book.datasource

import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import kotlinx.coroutines.flow.Flow

interface RemoteBookDataSource{
    suspend fun getBookDto(bookId: String): Flow<BookDto>
    suspend fun getBookDtos(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<BookDto>>
    suspend fun createBookDto(userId: String): Flow<String>
    suspend fun updateBookDto(bookDto: BookDto): Flow<Boolean>
    suspend fun deleteBook(bookIdList: List<String>): Flow<Boolean>
}