package com.tntt.user.datasource

import com.tntt.user.model.UserDto
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {
    suspend fun getUser(id: String): Flow<UserDto>
    suspend fun createUser(userDto: UserDto): Flow<String>
    suspend fun updateUser(userDto: UserDto): Flow<UserDto>
    suspend fun deleteUser(id: String): Flow<Boolean>
}