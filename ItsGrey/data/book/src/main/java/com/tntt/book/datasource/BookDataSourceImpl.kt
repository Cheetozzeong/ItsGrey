package com.tntt.book.datasource

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.book.model.datasource.BookDto
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class BookDataSourceImpl: BookDataSource {
    @Inject lateinit var firestore: FirebaseFirestore

    override fun createBook(book: BookDto): Boolean {
        var result: Boolean = true
        book.id = UUID.randomUUID().toString()
        firestore.collection("book")
            .document(book.id)
            .set(book.id)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener {
                    e ->
                {
                    Log.w(TAG, "Error writing document", e)
                    result = false
                }
            }
        return result
    }

    override fun readBookById(id: String): BookDto {
        val result = firestore.collection("book").document(id).get()
        println("result = ${result}")

        lateinit var book: BookDto

        // result -> book mapping 구현

        return book
    }

    override fun updateBook(book: BookDto): BookDto {

    }

    override fun deleteBook(id: String): Boolean {
        val result = firestore.collection("book").document(id).delete()
            .addOnSuccessListener { println("success delete") }
            .addOnFailureListener { println("fail delete") }

    }
}