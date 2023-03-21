package com.tntt.user.datasource

import com.tntt.user.model.datasource.UserDto

interface RemoteUserDataSource {
    fun addBook(bookId: String)

    fun getUser(userId: String): UserDto
    fun getInProgressBookIds(userId: String): ArrayList<String>
    fun getPublishedBookIds(userId: String): ArrayList<String>

    fun deleteBook(userId: String, bookId: String)
}