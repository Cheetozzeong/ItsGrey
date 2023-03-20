package com.tntt.book.datasource

import com.tntt.book.model.BookDto
import com.tntt.home.model.BookType
import com.tntt.home.model.SortType
import com.tntt.network.Firestore

class RemoteBookDataSourceImpl: RemoteBookDataSource {

    val bookCollection by lazy { Firestore.firestore.collection("book") }

    override fun getBookDtos(
        userId: String,
        sortType: SortType,
        startIndex: Int,
        bookType: BookType
    ): List<BookDto> {
        val bookDtos = mutableListOf<BookDto>()
        bookCollection.whereEqualTo("userId", userId).get()
    }

    override fun createBookDto(userId: String): String {
        TODO("Not yet implemented")
    }

    override fun deleteBook(bookIds: List<String>): Boolean {
        TODO("Not yet implemented")
    }
}