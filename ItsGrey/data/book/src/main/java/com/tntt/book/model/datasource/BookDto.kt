package com.tntt.book.model.datasource

import java.util.Date
import java.util.ArrayList

data class BookDto(
    val id: String,
    val title: String,
    val isPublished: Boolean,
    val publishDate: Date,
    val editDate: Date,
    val pages: ArrayList<String>)