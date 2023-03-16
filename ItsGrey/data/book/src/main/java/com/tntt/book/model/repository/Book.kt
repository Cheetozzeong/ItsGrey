package com.tntt.book.model.repository

import java.util.Date
import java.util.ArrayList

abstract class Book(
    open val id: String,
    open val title: String,
    open val pages: ArrayList<String>)