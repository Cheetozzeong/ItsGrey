package com.tntt.book.datasource

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.network.Firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RemoteBookDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteBookDataSource {

    val bookCollection by lazy { firestore.collection("book") }

    override suspend fun getBookDto(bookId: String): Flow<BookDto> = flow {
        var bookDto = BookDto("1", "1", "1", BookType.EDIT, Date())
        bookCollection.document(bookId).get().addOnCompleteListener { documentSnapshot ->
            val data = documentSnapshot.result?.data
            val id = data?.get("id") as String
            val userId = data?.get("userId") as String
            val title = data?.get("title") as String
            val bookType = BookType.valueOf(data?.get("bookType") as String)
            val saveDate = (data?.get("saveDate") as Timestamp).toDate()
            bookDto = BookDto(id, userId, title, bookType, saveDate)
        }.await()
        emit(bookDto)
 }

    override suspend fun getBookDtos(
        userId: String,
        sortType: SortType,
        startIndex: Long,
        bookType: BookType
    ): Flow<List<BookDto>> = flow {
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
    }

    override suspend fun createBookDto(userId: String): Flow<String> = flow {
        val bookId = UUID.randomUUID().toString()
        val bookDto = BookDto(bookId, userId, "Untitled", BookType.EDIT, Date())
        bookCollection
            .document(bookId)
            .set(bookDto)
            .addOnSuccessListener {
                Log.d("MyTag", "success... in bookCollection.document(${bookId}).set(${bookDto})")
            }
            .addOnFailureListener {
                    e -> Log.d("MyTag", "fail... in bookCollection.document(${bookId}).set(${bookDto})")
            }
        emit(bookId)
    }

    override suspend fun updateBookDto(bookDto: BookDto): Flow<Boolean> = flow {
        val bookCollection = firestore.collection("book")
        var result = true
        bookCollection
            .document(bookDto.id)
            .set(bookDto)
            .addOnFailureListener { result = false }
        emit(result)
    }

    override suspend fun deleteBook(bookIdList: List<String>): Flow<Boolean> = flow {
        val bookCollection = firestore.collection("book")
        var result = true
        for(bookId in bookIdList){
            bookCollection
                .document(bookId)
                .delete()
                .addOnFailureListener{
                    result = false
                }
        }
        emit(result)
    }
}