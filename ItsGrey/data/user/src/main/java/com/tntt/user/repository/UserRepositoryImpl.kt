package com.tntt.user.repository

import com.tntt.model.UserInfo
import com.tntt.repo.UserRepository
import com.tntt.user.datasource.RemoteUserDataSource
import com.tntt.user.datasource.RemoteUserDataSourceImpl
import com.tntt.user.model.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource
): UserRepository {

    override suspend fun getUser(id: String): Flow<UserInfo> = flow {
        remoteUserDataSource.getUser(id).collect() { userDto ->
            emit(UserInfo(userDto.id, userDto.name))
        }
    }

    override suspend fun createUser(userInfo: UserInfo): Flow<String> = flow {
        val userDto = UserDto(userInfo.id, userInfo.name)
        remoteUserDataSource.createUser(userDto).collect() { userId ->
            emit(userId)
        }
    }

    override suspend fun updateUser(userInfo: UserInfo): Flow<UserInfo> = flow {
        val userDto = UserDto(userInfo.id, userInfo.name)
        remoteUserDataSource.updateUser(userDto).collect() { updatedUserDto ->
            emit(UserInfo(updatedUserDto.id, updatedUserDto.name))
        }
    }

    override suspend fun deleteUser(id: String): Flow<Boolean> = flow {
        remoteUserDataSource.deleteUser(id).collect() { result ->
            emit(result)
        }
    }
}