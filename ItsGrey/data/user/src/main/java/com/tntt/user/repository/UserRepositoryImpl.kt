package com.tntt.user.repository

import com.tntt.model.UserInfo
import com.tntt.repo.UserRepository
import com.tntt.user.datasource.RemoteUserDataSourceImpl

class UserRepositoryImpl: UserRepository {
    override fun getUser(token: String): UserInfo {
        val userDto = RemoteUserDataSourceImpl.getUser(token)
        return UserInfo(userDto.name)
    }
}