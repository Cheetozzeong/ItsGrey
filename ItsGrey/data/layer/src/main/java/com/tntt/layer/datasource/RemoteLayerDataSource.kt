package com.tntt.layer.datasource

import android.graphics.Bitmap
import com.tntt.layer.model.LayerDto
import kotlinx.coroutines.flow.Flow

interface RemoteLayerDataSource {
    suspend fun createLayerDto(layerDto: LayerDto): Flow<String>
    suspend fun getLayerDtoList(imageBoxId: String): Flow<List<LayerDto>>
    suspend fun updateLayerDtoList(layerDtoList: List<LayerDto>): Flow<Boolean>
    suspend fun deleteLayerDtoList(imageBoxId: String): Flow<Boolean>
    suspend fun getSumLayer(imageBoxId: String): Flow<Bitmap>
}