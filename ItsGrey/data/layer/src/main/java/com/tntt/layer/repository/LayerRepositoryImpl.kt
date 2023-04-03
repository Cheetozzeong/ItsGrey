package com.tntt.layer.repository

import android.graphics.Bitmap
import android.net.Uri
import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.layer.model.LayerDto
import com.tntt.model.LayerInfo
import com.tntt.repo.LayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LayerRepositoryImpl @Inject constructor(
    private val layerDataSource: RemoteLayerDataSource,
    private val imageBoxDataSource: RemoteImageBoxDataSource
): LayerRepository {

    override suspend fun createLayerInfo(imageBoxId: String, layerInfo: LayerInfo): Flow<LayerInfo> = flow {
        layerDataSource.createLayerDto(LayerDto("", imageBoxId, layerInfo.order, layerInfo.bitmap)).collect() { layerDtoId ->
            emit(layerInfo)
        }
    }

    override suspend fun getLayerInfoList(imageBoxId: String): Flow<List<LayerInfo>> = flow {
        val layerInfoList = mutableListOf<LayerInfo>()
        layerDataSource.getLayerDtoList(imageBoxId).collect() { layerDtoList ->
            for (layerDto in layerDtoList) {
                layerInfoList.add(LayerInfo(layerDto.id, layerDto.order, layerDto.bitmap))
            }
            emit(layerInfoList)
        }
    }

    override suspend fun updateLayerInfoList(imageBoxId: String, layerInfoList: List<LayerInfo>): Flow<Boolean> = flow {
        val layerDtoList = mutableListOf<LayerDto>()

        for (layerInfo in layerInfoList) {
            layerDtoList.add(LayerDto(layerInfo.id, imageBoxId, layerInfo.order, layerInfo.bitmap))
        }
        layerDataSource.updateLayerDtoList(layerDtoList).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteLayerInfoList(imageBoxId: String): Flow<Boolean> = flow {
        layerDataSource.deleteLayerDtoList(imageBoxId).collect() { result ->
            emit(result)
        }
    }

    override suspend fun getSketchBitmap(uri: Uri): Flow<Bitmap> = flow {
        layerDataSource.getSketchBitmap(uri).collect() { bitmap ->
            emit(bitmap)
        }
    }

    override suspend fun saveImage(uri: Uri): Flow<Uri?> = flow {
        layerDataSource.saveImage(uri).collect() { result ->
            emit(result)
        }
    }

    override suspend fun getImage(uri: Uri): Flow<Bitmap> {
        TODO("Not yet implemented")
    }
}