package com.tntt.book.model.repository

import java.util.*

data class Book (
    var id:String?,
    var title:String?,
    var isPublished:Boolean?,
    var publishDate: Date?,
    var editDate: Date?,
    val pages:java.util.ArrayList<String>?){
}