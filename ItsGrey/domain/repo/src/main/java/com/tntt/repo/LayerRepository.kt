package com.tntt.repo

import android.graphics.Bitmap
import com.tntt.model.LayerInfo
import kotlinx.coroutines.flow.Flow

interface LayerRepository {
    suspend fun createLayerInfo(imageBoxId: String, layerInfo: LayerInfo): Flow<String>
    suspend fun getLayerInfoList(imageBoxId: String): Flow<List<LayerInfo>>
    suspend fun updateLayerInfoList(imageBoxId: String, layerInfoList: List<LayerInfo>): Flow<Boolean>
    suspend fun deleteLayerInfoList(imageBoxId: String): Flow<Boolean>
}