package com.tntt.book.model

import com.tntt.model.BookType
import java.util.Date
import java.util.ArrayList

data class BookDto(
    val id: String,
    val userId: String,
    val title: String,
    val bookType: BookType,
    val saveDate: Date,
)