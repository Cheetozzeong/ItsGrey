package com.tntt.book.datasource

import com.tntt.book.model.BookDto
import com.tntt.home.model.BookType
import com.tntt.home.model.SortType

interface RemoteBookDataSource{
    fun getBookDtos(userId: String, sortType: SortType, startIndex: Int, bookType: BookType): List<BookDto>
    fun createBookDto(userId: String): String
    fun deleteBook(bookIds: List<String>): Boolean
}