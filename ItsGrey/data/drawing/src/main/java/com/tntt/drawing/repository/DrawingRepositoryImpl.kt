package com.tntt.drawing.repository

import com.tntt.drawing.datasource.RemoteDrawingDataSource
import com.tntt.drawing.datasource.RemoteDrawingDataSourceImpl
import com.tntt.drawing.model.DrawingDto
import com.tntt.model.DrawingInfo
import com.tntt.repo.DrawingRepository

class DrawingRepositoryImpl : DrawingRepository {

    val drawingDataSource: RemoteDrawingDataSource by lazy { RemoteDrawingDataSourceImpl }

    override fun createDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): String {
        return drawingDataSource.createDrawingDto(DrawingDto(drawingInfo.id, imageBoxId, drawingInfo.penColor, drawingInfo.recentColors))
    }

    override fun getDrawingInfo(imageBoxId: String): DrawingInfo {
        val drawingDto = drawingDataSource.getDrawingDto(imageBoxId)
        return DrawingInfo(drawingDto.id, drawingDto.penColor, drawingDto.recentColors)
    }

    override fun updateDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Boolean {
        val drawingDto = DrawingDto(drawingInfo.id, imageBoxId, drawingInfo.penColor, drawingInfo.recentColors)
        return drawingDataSource.updateDrawingDto(drawingDto)
    }

    override fun deleteDrawingInfo(id: String): Boolean {
        return drawingDataSource.deleteDrawingDto(id)
    }
}