package com.tntt.book.model

import com.tntt.model.BookType
import java.util.Date
import java.util.ArrayList

data class BookDto(
    var id: String,
    var userId: String,
    var title: String,
    var bookType: BookType,
    var saveDate: Date,
)