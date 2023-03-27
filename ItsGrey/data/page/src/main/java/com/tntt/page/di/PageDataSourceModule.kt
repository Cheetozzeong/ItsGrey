package com.tntt.page.di

import com.tntt.page.datasource.RemotePageDataSource
import com.tntt.page.datasource.RemotePageDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PageDataSourceModule {

    @Binds
    fun bindsPageDataSource(
        pageDataSource: RemotePageDataSourceImpl,
    ): RemotePageDataSource
}