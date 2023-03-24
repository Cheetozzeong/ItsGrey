package com.tntt.page.di

import com.tntt.page.datasource.RemotePageDataSource
import com.tntt.page.datasource.RemotePageDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface PageDataSourceModule {

    @Binds
    fun bindsPageDataSource(
        pageDataSource: RemotePageDataSourceImpl,
    ): RemotePageDataSource
}