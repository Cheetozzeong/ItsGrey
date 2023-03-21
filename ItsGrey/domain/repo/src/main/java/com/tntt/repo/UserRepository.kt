package com.tntt.repo

import com.tntt.model.UserInfo

interface UserRepository {
    fun getUser(id: String): UserInfo
    fun createUser(userInfo: UserInfo): String
    fun updateUser(userInfo: UserInfo): UserInfo
    fun deleteUser(id: String): Boolean
}