package com.tntt.drawing.di

import com.tntt.drawing.datasource.RemoteDrawingDataSource
import com.tntt.drawing.datasource.RemoteDrawingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DrawingDataSourceModule {

    @Binds
    fun bindsDrawingDataSource(
        drawingDataSource: RemoteDrawingDataSourceImpl,
    ): RemoteDrawingDataSource
}