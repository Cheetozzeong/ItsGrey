package com.tntt.layer.datasource

import android.graphics.Bitmap
import android.net.Uri
import com.tntt.layer.model.LayerDto
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.InputStream

interface RemoteLayerDataSource {
    suspend fun createLayerDtoList(layerDtoList: List<LayerDto>): Flow<List<LayerDto>>
    suspend fun getLayerDtoList(imageBoxId: String): Flow<List<LayerDto>>
    suspend fun updateLayerDtoList(layerDtoList: List<LayerDto>): Flow<Boolean>
    suspend fun deleteLayerDtoList(imageBoxId: String): Flow<Boolean>
    suspend fun getSketchBitmap(bitmap: Bitmap): Flow<Bitmap>
    suspend fun saveImage(bitmap: Bitmap, url: String): Flow<Uri?>
    suspend fun getImage(uri: String): Flow<Bitmap>
}