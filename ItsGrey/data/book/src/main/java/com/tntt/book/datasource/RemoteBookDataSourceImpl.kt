package com.tntt.book.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.book.model.BookDto
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.network.Firestore
import java.util.*
import javax.inject.Inject

class RemoteBookDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteBookDataSource {

//    val bookCollection by lazy { firestore.collection("book") }

    override fun getBookDto(bookId: String): BookDto {
        println("RemoteBookDataSourceImpl.getBookDto(${bookId})")
        var bookDto: BookDto = BookDto("1","1","1", BookType.EDIT, Date())

        val bookCollection = firestore.collection("book")

        bookCollection
            .document(bookId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                println("documentSnapshot.data = ${documentSnapshot.data}")
//                val data = documentSnapshot.data
//                val id = data?.get("id") as String
//                val userId = data?.get("userId") as String
//                val title = data?.get("title") as String
//                val testBookType = data?.get("bookType")
//                val bookType = data?.get("bookType") as BookType
//                val saveDate = data?.get("saveDate") as Date
//                bookDto = BookDto(id, userId, title, bookType, saveDate)
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

        val bookCollection = firestore.collection("book")

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
        val bookCollection = firestore.collection("book")
        println("createBookDto(${userId})")
        val bookId = UUID.randomUUID().toString()
        val bookDto = BookDto(bookId, userId, "Untitled", BookType.EDIT, Date())
        println("in createBookDto.. bookDto = ${bookDto}")
        bookCollection
            .document(bookId)
            .set(bookDto)
            .addOnSuccessListener {
                Log.d("MyTag", "success... in bookCollection.document(${bookId}).set(${bookDto})")
            }
            .addOnFailureListener {
                e -> Log.d("MyTag", "fail... in bookCollection.document(${bookId}).set(${bookDto})")
            }
//        println("bookCollection = ${bookCollection}")
//        val bookDocument = bookCollection.document(bookId)
//        println("bookDocument = ${bookDocument}")
//        val documentReference = bookDocument.set(bookDto)
//        println("documentReference = ${documentReference}")



        println("after save")
        val testMap = hashMapOf<String, Int>("123" to 1)
        bookCollection
            .document(bookId)
            .set(testMap)
            .addOnSuccessListener { println("success... in bookCollection.document(${bookId}).set(${testMap})") }
            .addOnFailureListener { println("fail... in bookCollection.document(${bookId}).set(${testMap})") }
        println("after test save")
        return bookId
    }

    override fun updateBookDto(bookDto: BookDto): Boolean {
        val bookCollection = firestore.collection("book")
        var result = true
        bookCollection
            .document(bookDto.id)
            .set(bookDto)
            .addOnFailureListener { result = false }
        return result
    }

    override fun deleteBook(bookIdList: List<String>): Boolean {
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
        return result
    }
}