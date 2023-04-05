package com.tntt.repo

import com.tntt.model.DrawingInfo
import kotlinx.coroutines.flow.Flow

interface DrawingRepository {
    suspend fun createDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Flow<String>
    suspend fun getDrawingInfo(imageBoxId: String): Flow<DrawingInfo>
    suspend fun updateDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Flow<Boolean>
    suspend fun deleteDrawingInfo(imageBoxId: String): Flow<Boolean>
}