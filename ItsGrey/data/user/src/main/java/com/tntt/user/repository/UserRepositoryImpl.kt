package com.tntt.user.repository

import com.tntt.model.UserInfo
import com.tntt.repo.UserRepository
import com.tntt.user.datasource.RemoteUserDataSource
import com.tntt.user.datasource.RemoteUserDataSourceImpl
import com.tntt.user.model.UserDto
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource
): UserRepository {

    override fun getUser(id: String): UserInfo {
        val userDto = remoteUserDataSource.getUser(id)
        return UserInfo(userDto.id, userDto.name)
    }

    override fun createUser(userInfo: UserInfo): String {
        val userDto = UserDto(userInfo.id, userInfo.name)
        return remoteUserDataSource.createUser(userDto)
    }

    override fun updateUser(userInfo: UserInfo): UserInfo {
        val userDto = UserDto(userInfo.id, userInfo.name)
        val updatedUserDto = remoteUserDataSource.updateUser(userDto)
        return UserInfo(updatedUserDto.id, updatedUserDto.name)
    }

    override fun deleteUser(id: String): Boolean {
        return remoteUserDataSource.deleteUser(id)
    }
}