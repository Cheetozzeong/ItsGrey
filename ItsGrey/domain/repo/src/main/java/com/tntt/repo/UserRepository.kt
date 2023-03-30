package com.tntt.repo

import com.tntt.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(id: String): Flow<UserInfo>
    suspend fun createUser(userInfo: UserInfo): Flow<String>
    suspend fun updateUser(userInfo: UserInfo): Flow<UserInfo>
    suspend fun deleteUser(id: String): Flow<Boolean>
}