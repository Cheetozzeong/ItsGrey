package com.tntt.textbox.di

import com.tntt.textbox.datasource.RemoteTextBoxDataSource
import com.tntt.textbox.datasource.RemoteTextBoxDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TextBoxDataSourceModule {

    @Binds
    fun bindsTextBoxDataSource(
        textBoxDataSource: RemoteTextBoxDataSourceImpl,
    ): RemoteTextBoxDataSource
}