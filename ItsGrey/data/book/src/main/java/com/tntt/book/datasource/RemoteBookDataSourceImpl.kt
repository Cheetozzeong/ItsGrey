package com.tntt.book.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.network.Firestore
import java.util.*
import javax.inject.Inject

class RemoteBookDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
): RemoteBookDataSource {

    val bookCollection by lazy { firestore.collection("book") }

    override fun getBookDto(bookId: String): BookDto {
        lateinit var bookDto: BookDto
        bookCollection
            .document(bookId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val data = documentSnapshot.data
                val id = data?.get("id") as String
                val userId = data?.get("userId") as String
                val title = data?.get("data") as String
                val bookType = data?.get("bookType") as BookType
                val saveDate = data?.get("saveDate") as Date
                bookDto = BookDto(id, userId, title, bookType, saveDate)
            }
        return bookDto
    }

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
                val id = document.get("id") as String
                val userId = document.get("userId") as String
                val title = document.get("title") as String
                val bookType = document.get("bookType") as BookType
                val saveDate = document.getDate("saveDate") as Date
                bookDtos.add(BookDto(id, userId, title, bookType, saveDate))
            }
        }
        return bookDtos
    }

    override fun createBookDto(userId: String): String {
        val bookId = UUID.randomUUID().toString()
        val bookDto = BookDto(bookId, userId, "Untitled", BookType.EDIT, Date())
        bookCollection
            .document(bookId)
            .set(bookDto)
        return bookId
    }

    override fun updateBookDto(bookDto: BookDto): Boolean {
        var result = true
        bookCollection
            .document(bookDto.id)
            .set(bookDto)
            .addOnFailureListener { result = false }
        return result
    }

    override fun deleteBook(bookIdList: List<String>): Boolean {
        var result = true
        for(bookId in bookIdList){
            bookCollection
                .document(bookId)
                .delete()
                .addOnFailureListener{
                    result = false
                }
        }
        return result
    }
}