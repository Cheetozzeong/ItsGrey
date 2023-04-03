package com.tntt.repo

import android.graphics.Bitmap
import android.net.Uri
import com.tntt.model.LayerInfo
import kotlinx.coroutines.flow.Flow

interface LayerRepository {
    suspend fun createLayerInfo(imageBoxId: String, layerInfo: LayerInfo): Flow<LayerInfo>
    suspend fun getLayerInfoList(imageBoxId: String): Flow<List<LayerInfo>>
    suspend fun updateLayerInfoList(imageBoxId: String, layerInfoList: List<LayerInfo>): Flow<Boolean>
    suspend fun deleteLayerInfoList(imageBoxId: String): Flow<Boolean>
    suspend fun getSketchBitmap(uri: Uri): Flow<Bitmap>
    suspend fun saveImage(uri: Uri): Flow<Uri?>
    suspend fun getImage(uri: Uri): Flow<Bitmap>
}