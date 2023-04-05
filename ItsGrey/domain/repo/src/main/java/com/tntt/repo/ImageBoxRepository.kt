package com.tntt.repo

import android.graphics.Bitmap
import com.tntt.model.ImageBoxInfo
import kotlinx.coroutines.flow.Flow

interface ImageBoxRepository {
    suspend fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<String>
    suspend fun getImageBoxInfoList(pageId: String): Flow<List<ImageBoxInfo>>
    suspend fun updateImageBoxInfoList(pageId: String, imageBoxInfoList: List<ImageBoxInfo>): Flow<Boolean>
    suspend fun deleteImageBoxInfo(id: String):  Flow<Boolean>
    suspend fun setImage(imageBoxId: String, image: Bitmap): Flow<Boolean>
}