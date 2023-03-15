package com.tntt.user.repository

import com.tntt.user.model.repository.User

interface UserRepository {
    fun createBook(id: String, bookId: String): Boolean

    fun getUser(id: String): User
    fun getInProgressBooks(id: String): ArrayList<String>
    fun getPublishedBooks(id: String): ArrayList<String>

    fun deleteBook(id: String, bookId: String): Boolean
}