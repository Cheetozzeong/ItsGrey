package com.tntt.repo

import com.tntt.model.BookInfo

interface BookRepository {
    fun getBookInfos(val userId: String, val sortType: SortType, val startIndex: Int, bookType: BookType): List<BookInfo>
}