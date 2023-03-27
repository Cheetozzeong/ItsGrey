package com.tntt.repo

import com.tntt.model.DrawingInfo

interface DrawingRepository {
    fun createDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): String
    fun getDrawingInfo(imageBoxId: String): DrawingInfo
    fun updateDrawingInfo(imageBoxId: String, drawingInfo: DrawingInfo): Boolean
    fun deleteDrawingInfo(id: String): Boolean
}