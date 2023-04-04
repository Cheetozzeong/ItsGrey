package com.tntt.book.datasource

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RemoteBookDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteBookDataSource {

    val bookCollection by lazy { firestore.collection("book") }

    override suspend fun createBookDto(userId: String, bookDto: BookDto): Flow<String> = flow {
        bookCollection
            .document(bookDto.id)
            .set(bookDto)
            .await()
        emit(bookDto.id)
    }

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

    override suspend fun getBookDtoList(userId: String, sortType: SortType, startIndex: Long, bookType: BookType): Flow<List<BookDto>> = flow {
        var order = sortType.order
        var by = sortType.by

        val bookDtoList = mutableListOf<BookDto>()
        val query = bookCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("bookType", bookType)
            .orderBy(by, order)
            .limit(20)

        if(startIndex > 0) {
            val startAfterDocument = bookCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("bookType", bookType)
                .orderBy("title", Query.Direction.ASCENDING)
                .limit(startIndex + 1)
                .get()
                .await()

            query.startAfter(startAfterDocument.documents?.last())
        }

        query.get().addOnSuccessListener {documents ->
            for(document in documents){
                val id = document.get("id") as String
                val userId = document.get("userId") as String
                val title = document.get("title") as String
                val bookType = BookType.valueOf(document.get("bookType") as String)
                val saveDate = (document.getDate("saveDate") as Timestamp).toDate()
                bookDtoList.add(BookDto(id, userId, title, bookType, saveDate))
            }
        }.await()
        emit(bookDtoList)
    }

    override suspend fun updateBookDto(bookDto: BookDto): Flow<Boolean> = flow {
        var result = true
        bookCollection
            .document(bookDto.id)
            .set(bookDto)
            .addOnFailureListener { result = false }
            .await()
        emit(result)
    }

    override suspend fun deleteBookDto(bookIdList: List<String>): Flow<Boolean> = flow {
        var result = true
        for(bookId in bookIdList){
            bookCollection
                .document(bookId)
                .delete()
                .addOnFailureListener{
                    result = false
                }.await()
        }
        emit(result)
    }
}