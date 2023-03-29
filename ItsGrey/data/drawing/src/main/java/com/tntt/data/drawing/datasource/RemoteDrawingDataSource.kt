package com.tntt.data.drawing.datasource

import com.tntt.data.drawing.model.DrawingDto
import kotlinx.coroutines.flow.Flow

interface RemoteDrawingDataSource {
    suspend fun createDrawingDto(drawingDto: DrawingDto): Flow<String>
    suspend fun getDrawingDto(imageBoxId: String): Flow<DrawingDto>
    suspend fun updateDrawingDto(drawingDto: DrawingDto): Flow<Boolean>
    suspend fun deleteDrawingDto(id: String): Flow<Boolean>
}