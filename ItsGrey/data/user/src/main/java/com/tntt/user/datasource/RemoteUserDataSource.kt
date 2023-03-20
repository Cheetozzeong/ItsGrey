package com.tntt.user.datasource

import com.tntt.user.model.datasource.UserDto

interface RemoteUserDataSource {
    fun getUser(token: String): UserDto
}