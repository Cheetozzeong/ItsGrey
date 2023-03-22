package com.tntt.repo

import com.tntt.home.model.BookType
import com.tntt.home.model.SortType
import com.tntt.model.BookInfo

interface BookRepository {
    fun getBookInfos(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): List<BookInfo>
    fun createBook(userId: String): String
    fun deleteBook(bookIds: List<String>): Boolean

}