package com.tntt.book.di

import com.tntt.book.datasource.RemoteBookDataSource
import com.tntt.book.datasource.RemoteBookDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BookDataSourceModule {

    @Binds
    fun bindsBookDataSource(
        bookDataSource: RemoteBookDataSourceImpl,
    ): RemoteBookDataSource
}