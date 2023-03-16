package com.tntt.book.model.repository

import java.util.*

data class BookForPublished(
    override val id: String,
    override val title: String,
    override val pages: ArrayList<String>,
    val publishDate: Date): Book(id, title, pages)