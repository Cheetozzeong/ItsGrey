package com.tntt.drawing.repository

import com.tntt.drawing.datasource.RemoteDrawingDataSource
import com.tntt.drawing.datasource.RemoteDrawingDataSourceImpl
import com.tntt.drawing.model.DrawingDto
import com.tntt.model.DrawingInfo
import com.tntt.repo.DrawingRepository
import javax.inject.Inject

class DrawingRepositoryImpl @Inject constructor(
    private val drawingDataSource: RemoteDrawingDataSource
): DrawingRepository {

    override fun createDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): String {
        return drawingDataSource.createDrawingDto(DrawingDto(drawingInfo.id, imageBoxId, drawingInfo.penSizeList, drawingInfo.eraserSizeList, drawingInfo.penColor, drawingInfo.recentColorList))
    }

    override fun getDrawingInfo(imageBoxId: String): DrawingInfo {
        val drawingDto = drawingDataSource.getDrawingDto(imageBoxId)
        return DrawingInfo(drawingDto.id, drawingDto.penSizeList, drawingDto.eraserSizeList, drawingDto.penColor, drawingDto.recentColors)
    }

    override fun updateDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Boolean {
        val drawingDto = DrawingDto(drawingInfo.id, imageBoxId, drawingInfo.penSizeList, drawingInfo.eraserSizeList, drawingInfo.penColor, drawingInfo.recentColorList)
        return drawingDataSource.updateDrawingDto(drawingDto)
    }

    override fun deleteDrawingInfo(id: String): Boolean {
        return drawingDataSource.deleteDrawingDto(id)
    }
}