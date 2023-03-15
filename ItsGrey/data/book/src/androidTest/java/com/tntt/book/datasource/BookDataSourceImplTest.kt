package com.tntt.book.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tntt.book.model.datasource.BookDto
import org.junit.Test

class BookDataSourceImplTest {
    @Test
    public fun createBookTest(){
        val bookDataSource = BookDataSourceImpl()
        bookDataSource.firestore = Firebase.firestore

        var book = BookDto(null, null, null,null,null,null)
        book.id= "1"
        book.title="테스트"

        var result = bookDataSource.createBook(book)
        println("result = ${result}")
    }

}