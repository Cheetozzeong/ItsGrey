package com.tntt.domain.drawing.usecase

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.tntt.model.LayerInfo
import com.tntt.repo.DrawingRepository
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.LayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class DrawingUseCase @Inject constructor(
    private val drawingRepository: DrawingRepository,
    private val layerRepository: LayerRepository,
    private val imageBoxRepository: ImageBoxRepository,
){

    suspend fun getSketch(bitmap: Bitmap): Flow<Bitmap> = flow {
        layerRepository.getSketchBitmap(bitmap).collect() { bitmap ->
            emit(bitmap)
        }
    }

    suspend fun saveLayerList(imageBoxId: String, layerInfoList: List<LayerInfo>): Flow<Boolean> = flow {
        for (layerInfo in layerInfoList) {
            saveImage(layerInfo.bitmap, layerInfo.url).collect() { url ->
                Log.d("function test", "saveImage url = ${url}")
            }
        }
        layerRepository.updateLayerInfoList(imageBoxId, layerInfoList).collect() { result ->
            emit(result)
        }
    }

    suspend fun saveImage(bitmap: Bitmap, url: String): Flow<Uri?> = flow {
        layerRepository.saveImage(bitmap, url).collect() { result ->
            emit(result)
        }
    }

    suspend fun getImage(url: String): Flow<Bitmap> = flow {
        layerRepository.getImage(url).collect() { bitmap ->
            emit(bitmap)
        }
    }
}