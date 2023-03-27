package com.tntt.repo

import com.tntt.model.BookInfo
import com.tntt.model.BookType
import com.tntt.model.SortType

interface BookRepository {
    fun getBookInfos(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): List<BookInfo>
    fun createBook(userId: String): String
    fun deleteBook(bookIds: List<String>): Boolean

}