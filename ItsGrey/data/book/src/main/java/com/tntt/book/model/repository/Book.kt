package com.tntt.book.model.repository

import java.util.Date
import java.util.ArrayList

data class Book(
    val id: String,
    val title: String,
    val isPublished: Boolean,
    val publishDate: Date,
    val editDate: Date,
    val pages: ArrayList<String>)