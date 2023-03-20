package com.tntt.repo

import com.tntt.model.UserInfo

interface UserRepository {
    fun getUser(token: String): UserInfo
}