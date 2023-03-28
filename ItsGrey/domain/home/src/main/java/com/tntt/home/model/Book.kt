package com.tntt.home.model

import com.tntt.model.BookInfo
import com.tntt.model.Thumbnail

data class Book(
    val bookInfo: BookInfo,
    val thumbnail: Thumbnail?,
)