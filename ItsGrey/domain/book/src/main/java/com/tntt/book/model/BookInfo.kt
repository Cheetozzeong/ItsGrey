package com.tntt.book.model

import java.util.*
import kotlin.collections.ArrayList

data class BookInfo(
    val id: String,
    val title: String,
    val pages: ArrayList<String>,
    val saveDate: Date
)