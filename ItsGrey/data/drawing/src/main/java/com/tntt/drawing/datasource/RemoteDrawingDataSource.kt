package com.tntt.drawing.datasource

import com.tntt.drawing.model.DrawingDto

interface RemoteDrawingDataSource {
    fun createDrawingDto(drawingDto: DrawingDto): String
    fun getDrawingDto(imageBoxId: String): DrawingDto
    fun updateDrawingDto(drawingDto: DrawingDto): Boolean
    fun deleteDrawingDto(id: String): Boolean
}