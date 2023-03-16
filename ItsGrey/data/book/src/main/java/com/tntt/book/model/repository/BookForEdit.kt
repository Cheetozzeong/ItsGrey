package com.tntt.book.model.repository

import java.util.*

data class BookForEdit(
    override val id: String,
    override val title: String,
    override val pages: ArrayList<String>,
    val editDate: Date): Book(id, title, pages)