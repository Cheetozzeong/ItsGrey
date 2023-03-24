package com.tntt.imagebox.di

import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.datasource.RemoteImageBoxDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageBoxDataSourceModule {

    @Binds
    fun bindsImageBoxDataSource(
        imageBoxDataSource: RemoteImageBoxDataSourceImpl,
    ): RemoteImageBoxDataSource
}