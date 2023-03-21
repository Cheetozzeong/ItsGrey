package com.tntt.editbook.model

import com.tntt.model.BookInfo

data class Book(val bookInfo: BookInfo,
                val pages: List<Page>)