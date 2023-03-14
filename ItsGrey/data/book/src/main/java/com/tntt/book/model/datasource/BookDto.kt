package com.tntt.book.model.datasource

import java.util.Date

data class BookDto (
    var id:String?,
    var title:String?,
    var isPublished:Boolean?,
    var publishDate:Date?,
    var editDate:Date?,
    val pages:java.util.ArrayList<String>?){
}