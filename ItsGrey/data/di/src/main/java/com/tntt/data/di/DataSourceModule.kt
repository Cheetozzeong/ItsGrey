package com.tntt.data.di

import com.tntt.book.datasource.RemoteBookDataSource
import com.tntt.book.datasource.RemoteBookDataSourceImpl
import com.tntt.drawing.datasource.RemoteDrawingDataSource
import com.tntt.drawing.datasource.RemoteDrawingDataSourceImpl
import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.datasource.RemoteImageBoxDataSourceImpl
import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.layer.datasource.RemoteLayerDataSourceImpl
import com.tntt.page.datasource.RemotePageDataSource
import com.tntt.page.datasource.RemotePageDataSourceImpl
import com.tntt.repo.*
import com.tntt.textbox.datasource.RemoteTextBoxDataSource
import com.tntt.textbox.datasource.RemoteTextBoxDataSourceImpl
import com.tntt.user.datasource.RemoteUserDataSource
import com.tntt.user.datasource.RemoteUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {




    @Binds
    fun bindsTextBoxDataSource(
        textBoxDataSource: RemoteTextBoxDataSourceImpl,
    ): RemoteTextBoxDataSource

    @Binds
    fun bindsUserDataSource(
        userDataSource: RemoteUserDataSourceImpl,
    ): RemoteUserDataSource
}