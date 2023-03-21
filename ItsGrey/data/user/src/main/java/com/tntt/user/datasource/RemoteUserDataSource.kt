package com.tntt.user.datasource

import com.tntt.user.model.UserDto

interface RemoteUserDataSource {
    fun getUser(id: String): UserDto
    fun createUser(userDto: UserDto): String
    fun updateUser(userDto: UserDto): UserDto
    fun deleteUser(id: String): Boolean
}