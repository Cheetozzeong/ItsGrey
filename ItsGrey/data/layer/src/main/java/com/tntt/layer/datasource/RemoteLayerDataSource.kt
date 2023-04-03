package com.tntt.layer.datasource

import android.graphics.Bitmap
import android.net.Uri
import com.tntt.layer.model.LayerDto
import kotlinx.coroutines.flow.Flow

interface RemoteLayerDataSource {
    suspend fun createLayerDto(layerDto: LayerDto): Flow<LayerDto>
    suspend fun getLayerDtoList(imageBoxId: String): Flow<List<LayerDto>>
    suspend fun updateLayerDtoList(layerDtoList: List<LayerDto>): Flow<Boolean>
    suspend fun deleteLayerDtoList(imageBoxId: String): Flow<Boolean>
    suspend fun getSumLayer(imageBoxId: String): Flow<Bitmap>
    suspend fun getSketchBitmap(uri: Uri): Flow<Bitmap>
    suspend fun saveImage(uri: Uri): Flow<Uri?>
    suspend fun getImage(uri: Uri): Flow<Bitmap>
}