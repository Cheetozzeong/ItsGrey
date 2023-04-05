package com.tntt.domain.drawing.usecase

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.util.Log
import com.tntt.domain.drawing.model.ImageBox
import com.tntt.model.DrawingInfo
import com.tntt.model.ImageBoxInfo
import com.tntt.model.LayerInfo
import com.tntt.repo.DrawingRepository
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.LayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrawingUseCase @Inject constructor(
    private val drawingRepository: DrawingRepository,
    private val layerRepository: LayerRepository,
    private val imageBoxRepository: ImageBoxRepository,
){

    suspend fun createLayerList(imageBoxId: String, bitmap: Bitmap): Flow<List<LayerInfo>> = flow {
        val layerList = mutableListOf<LayerInfo>()

        val drawingBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(drawingBitmap)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        val drawingLayer = LayerInfo(0, drawingBitmap)
        layerRepository.getSketchBitmap(bitmap).collect() { sketchBitmap ->
            val sketchLayer = LayerInfo(1, sketchBitmap)
            layerList.add(drawingLayer)
            layerList.add(sketchLayer)
            layerRepository.createLayerInfoList(imageBoxId, layerList).collect() { layerInfoList ->
                emit(layerInfoList)
            }
        }
    }

    suspend fun getLayerList(imageBoxId: String): Flow<List<LayerInfo>> = flow {
        layerRepository.getLayerInfoList(imageBoxId).collect() { layerList ->
            emit(layerList)
        }
    }

    suspend fun getDrawing(imageBoxId: String): Flow<DrawingInfo> = flow {
        drawingRepository.getDrawingInfo(imageBoxId).collect() { drawingInfo ->
            emit(drawingInfo)
        }
    }

    suspend fun updateLayerList(imageBoxId: String, layerInfoList: List<LayerInfo>): Flow<Boolean> = flow {
        layerRepository.updateLayerInfoList(imageBoxId, layerInfoList).collect() { result ->
            emit(result)
        }
    }

    suspend fun save(pageId: String, imageBox: ImageBox): Flow<Boolean> = flow {
        drawingRepository.updateDrawingInfo(imageBox.id, imageBox.drawing).collect() { updateDrawingResult ->
            layerRepository.updateLayerInfoList(imageBox.id, imageBox.layerList).collect() { updateLayerResult ->
                layerRepository.getSumLayerBitmap(imageBox.layerList).collect() { sumLayer ->
                    imageBoxRepository.updateImageBoxInfo(pageId, ImageBoxInfo(imageBox.id, imageBox.boxData, sumLayer)).collect() { result ->
                        emit(result)
                    }
                }
            }
        }
    }
}