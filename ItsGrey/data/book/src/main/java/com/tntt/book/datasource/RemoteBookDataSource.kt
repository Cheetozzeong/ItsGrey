package com.tntt.book.datasource

import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType

interface RemoteBookDataSource{
    fun getBookDto(bookId: String): BookDto
    fun getBookDtos(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): List<BookDto>
    fun createBookDto(userId: String): String
    fun updateBookDto(bookDto: BookDto): Boolean
    fun deleteBook(bookIdList: List<String>): Boolean
}