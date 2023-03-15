package com.tntt.user.datasource

import com.tntt.user.model.datasource.UserDto

interface RemoteUserDataSource {
    fun createBook(id: String, bookId: String): Boolean

    fun getUser(id: String): UserDto
    fun getInProgressBooks(id: String): ArrayList<String>
    fun getPublishedBooks(id: String): ArrayList<String>

    fun deleteBook(id: String, bookId: String): Boolean
}