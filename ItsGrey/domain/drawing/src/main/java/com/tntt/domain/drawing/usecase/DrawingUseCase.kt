package com.tntt.domain.drawing.usecase

import android.graphics.Bitmap
import android.graphics.Color
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
    suspend fun createLayerList(bitmap: Bitmap, imageBoxInfo: ImageBoxInfo): Flow<List<LayerInfo>> = flow {
        // 밑그림 검출 서버와 통신하여 밑그림 얻어오기

        val layerInfoList = mutableListOf<LayerInfo>()

        val whiteBoard = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        whiteBoard.eraseColor(Color.WHITE)
        val drawingLayer = LayerInfo("", 1, whiteBoard)
        layerRepository.createLayerInfo(imageBoxInfo.id, drawingLayer).collect() { drawingLayerInfo ->
            layerInfoList.add(drawingLayerInfo)
            layerRepository.getSketchBitmap(bitmap).collect() { sketchBitmap ->
                val sketchLayer = LayerInfo("", 2, sketchBitmap)
                layerRepository.createLayerInfo(imageBoxInfo.id, sketchLayer).collect() { sketchLayerInfo ->
                    layerInfoList.add(sketchLayerInfo)
                }
                emit(layerInfoList)
            }
        }
    }

    suspend fun createDrawing(imageBoxId: String): Flow<DrawingInfo> = flow {
        val penSizeList = listOf<Int>(8, 12, 16)
        val eraseSizeList = listOf<Int>(8, 12, 16)
        val penColor = "#000000"
        val recentColorList = mutableListOf<String>()
        val drawingInfo = DrawingInfo("", penSizeList, eraseSizeList, penColor, recentColorList)
        drawingRepository.createDrawingInfo(imageBoxId, drawingInfo)
        emit(drawingInfo)
    }

    suspend fun saveDrawing(pageId: String, imageBox: ImageBox): Flow<Boolean> = flow {
//        layerRepository.updateLayerInfoList(imageBox.id, imageBox.layerList).collect() { layerResult ->
//            drawingRepository.updateDrawingInfo(imageBox.id, imageBox.drawing).collect() { drawingResult ->
//                imageBoxRepository.updateImageBoxInfo(pageId, ImageBoxInfo(imageBox.id, imageBox.boxData)).collect() { imageBoxResult ->
//                    emit(layerResult && drawingResult && imageBoxResult)
//                }
//            }
//        }
    }

    suspend fun retrofitTest(): Flow<String> = flow {
        layerRepository.retrofitTest().collect() { result ->
            emit(result)
        }
    }
}