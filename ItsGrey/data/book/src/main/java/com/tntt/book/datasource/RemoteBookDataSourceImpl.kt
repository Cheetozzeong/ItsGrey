package com.tntt.book.datasource

import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.network.Firestore
import java.util.*

class RemoteBookDataSourceImpl: RemoteBookDataSource {

    val bookCollection by lazy { Firestore.firestore.collection("book") }

    override fun getBookDtos(
        userId: String,
        sortType: SortType,
        startIndex: Long,
        bookType: BookType
    ): List<BookDto> {
        var order = sortType.order
        var by = sortType.by

        val bookDtos = mutableListOf<BookDto>()
        val query = bookCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("bookType", bookType)
            .orderBy(by, order)
            .limit(20)

        if(startIndex > 0) {
            val startAfterDocument = bookCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("bookType", bookType)
                .orderBy(by, order)
                .limit(startIndex)
                .get()

            query.startAfter(startAfterDocument.result?.documents?.last())
        }

        query.get().addOnSuccessListener {documents ->
            for(document in documents){
                bookDtos.add(BookDto(document.get("id").toString(), document.get("userId").toString(), document.get("title").toString(), document.getBoolean("isPublished")?:false, document.getTimestamp("saveDate")?.toDate()?: Date(0)))
            }
        }
        return bookDtos
    }

    override fun createBookDto(userId: String): String {
        val bookId = UUID.randomUUID().toString()
        val bookDto = BookDto(bookId, userId, "Untitled", false, Date())
        bookCollection.add(bookDto)
        return bookId
    }

    override fun deleteBook(bookIds: List<String>): Boolean {
        var result = true
        for(bookId in bookIds){
            bookCollection.document(bookId).delete().addOnFailureListener{ result = false }
        }
        return result
    }
}