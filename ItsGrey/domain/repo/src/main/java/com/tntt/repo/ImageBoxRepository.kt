package com.tntt.repo

import com.tntt.model.ImageBoxInfo
import kotlinx.coroutines.flow.Flow

interface ImageBoxRepository {
    suspend fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<ImageBoxInfo>
    suspend fun getImageBoxInfoList(pageId: String): Flow<List<ImageBoxInfo>>
    suspend fun updateImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<Boolean>
    suspend fun deleteImageBoxInfo(id: String):  Flow<Boolean>
}