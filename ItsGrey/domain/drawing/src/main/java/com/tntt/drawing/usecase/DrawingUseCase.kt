package com.tntt.drawing.usecase

import android.graphics.Bitmap
import com.tntt.drawing.model.ImageBox
import com.tntt.model.DrawingInfo
import com.tntt.model.ImageBoxInfo
import com.tntt.model.LayerInfo
import com.tntt.repo.DrawingRepository
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.LayerRepository
import javax.inject.Inject

class DrawingUseCase @Inject constructor(
    private val drawingRepository: DrawingRepository,
    private val layerRepository: LayerRepository,
    private val imageBoxRepository: ImageBoxRepository,
){
    fun createLayerList(bitmap: Bitmap, imageBoxInfo: ImageBoxInfo): List<LayerInfo> {
        // 밑그림 검출 서버와 통신하여 밑그림 얻어오기
        lateinit var sketchBitmap: Bitmap

        val drawingLayer = LayerInfo("", 1, bitmap)
        drawingLayer.id = layerRepository.createLayerInfo(imageBoxInfo.id, drawingLayer)
        val sketchLayer = LayerInfo("", 2, sketchBitmap)
        sketchLayer.id = layerRepository.createLayerInfo(imageBoxInfo.id, sketchLayer)

        return listOf<LayerInfo>(drawingLayer, sketchLayer)
    }

    fun createDrawing(imageBoxId: String): DrawingInfo {
        val penSizeList = listOf<Int>(8, 12, 16)
        val eraseSizeList = listOf<Int>(8, 12, 16)
        val penColor = "#000000"
        val recentColorList = mutableListOf<String>()
        val drawingInfo = DrawingInfo("", penSizeList, eraseSizeList, penColor, recentColorList)
        drawingInfo.id = drawingRepository.createDrawingInfo(imageBoxId, drawingInfo)
        return drawingInfo
    }

    fun createImageBox(bitmap: Bitmap, imageBoxInfo: ImageBoxInfo): ImageBox {
        val layerList = createLayerList(bitmap, imageBoxInfo)
        val drawing = createDrawing(imageBoxInfo.id)
        return ImageBox(imageBoxInfo.id, layerList, drawing, imageBoxInfo.boxData)
    }

    fun saveImageBox(pageId: String, imageBox: ImageBox): Boolean {
        return (layerRepository.updateLayerInfoList(imageBox.id, imageBox.layerList) &&
        drawingRepository.updateDrawingInfo(imageBox.id, imageBox.drawing) &&
        imageBoxRepository.updateImageBoxInfo(pageId, ImageBoxInfo(imageBox.id, imageBox.boxData)))
    }
}