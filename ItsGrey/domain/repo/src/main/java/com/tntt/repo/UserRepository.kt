package com.tntt.repo

import com.tntt.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(userInfo: UserInfo): Flow<String>
    suspend fun getUser(userId: String): Flow<UserInfo>
    suspend fun updateUser(userInfo: UserInfo): Flow<Boolean>
    suspend fun deleteUser(userId: String): Flow<Boolean>
}