package com.tntt.user.di

import com.tntt.user.datasource.RemoteUserDataSource
import com.tntt.user.datasource.RemoteUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserDataSourceModule {

    @Binds
    fun bindsUserDataSource(
        userDataSource: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource
}