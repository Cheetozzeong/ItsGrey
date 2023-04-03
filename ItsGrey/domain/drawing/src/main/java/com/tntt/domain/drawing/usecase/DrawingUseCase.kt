package com.tntt.domain.drawing.usecase

import android.graphics.Bitmap
import android.net.Uri
import com.tntt.model.LayerInfo
import com.tntt.repo.DrawingRepository
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.LayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrawingUseCase @Inject constructor(
    private val drawingRepository: DrawingRepository,
    private val layerRepository: LayerRepository,
    private val imageBoxRepository: ImageBoxRepository,
){

    suspend fun getSketch(uri: Uri): Flow<Bitmap> = flow {
        layerRepository.getSketchBitmap(uri).collect() { bitmap ->
            emit(bitmap)
        }
    }

    suspend fun saveImage(layerInfo: LayerInfo): Flow<Boolean> = flow {


    }

    suspend fun saveImage(uri: Uri): Flow<Uri?> = flow {
        layerRepository.saveImage(uri).collect() { result ->
            emit(result)
        }
    }
}