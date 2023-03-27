package com.tntt.layer.di

import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.layer.datasource.RemoteLayerDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LayerDataSourceModule {

    @Binds
    fun bindsLayerDataSource(
        layerDataSource: RemoteLayerDataSourceImpl,
    ): RemoteLayerDataSource
}