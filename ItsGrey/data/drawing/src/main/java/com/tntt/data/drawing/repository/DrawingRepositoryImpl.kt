package com.tntt.data.drawing.repository

import com.tntt.data.drawing.datasource.RemoteDrawingDataSource
import com.tntt.data.drawing.datasource.RemoteDrawingDataSourceImpl
import com.tntt.data.drawing.model.DrawingDto
import com.tntt.model.DrawingInfo
import com.tntt.repo.DrawingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrawingRepositoryImpl @Inject constructor(
    private val drawingDataSource: RemoteDrawingDataSource
): DrawingRepository {

    override suspend fun createDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Flow<String> = flow {
        drawingDataSource
            .createDrawingDto(DrawingDto(drawingInfo.id, imageBoxId, drawingInfo.penSizeList, drawingInfo.eraserSizeList, drawingInfo.penColor, drawingInfo.recentColorList)).collect() { drawingId ->
                emit(drawingId)
            }
    }

    override suspend fun getDrawingInfo(imageBoxId: String): Flow<DrawingInfo> = flow {
        drawingDataSource.getDrawingDto(imageBoxId).collect() { drawingDto ->
            emit(DrawingInfo(drawingDto.id, drawingDto.penSizeList, drawingDto.eraserSizeList, drawingDto.penColor, drawingDto.recentColors))
        }
    }

    override suspend fun updateDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Flow<Boolean> = flow {
        val drawingDto = DrawingDto(drawingInfo.id, imageBoxId, drawingInfo.penSizeList, drawingInfo.eraserSizeList, drawingInfo.penColor, drawingInfo.recentColorList)
        drawingDataSource.updateDrawingDto(drawingDto).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteDrawingInfo(imageBoxId: String): Flow<Boolean> = flow {
        drawingDataSource.deleteDrawingDto(imageBoxId).collect() { result ->
            emit(result)
        }
    }
}