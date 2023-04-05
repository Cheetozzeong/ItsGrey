package com.tntt.user.datasource

import com.tntt.user.model.UserDto
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {
    suspend fun createUser(userDto: UserDto): Flow<String>
    suspend fun getUser(userId: String): Flow<UserDto>
    suspend fun updateUser(userDto: UserDto): Flow<Boolean>
    suspend fun deleteUser(userId: String): Flow<Boolean>
}