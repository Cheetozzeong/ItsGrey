package com.tntt.imagebox.repository

import android.util.Log
import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.ImageBoxInfo
import com.tntt.repo.ImageBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageBoxRepositoryImpl @Inject constructor(
    private val imageBoxDataSource: RemoteImageBoxDataSource
) : ImageBoxRepository {

    override suspend fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<ImageBoxInfo> = flow {
        Log.d("function test", "createImageBoxInfo(${pageId}, ${imageBoxInfo})")
        val imageBoxDto = ImageBoxDto(imageBoxInfo.id, pageId, imageBoxInfo.boxData, imageBoxInfo.image)
        imageBoxDataSource.createImageBoxDto(imageBoxDto).collect() { imageBoxDto ->
            emit(ImageBoxInfo(imageBoxDto.id, imageBoxDto.boxData, imageBoxDto.image))
        }
    }

    override suspend fun getImageBoxInfoList(pageId: String): Flow<List<ImageBoxInfo>> = flow {
        val imageBoxInfoList = mutableListOf<ImageBoxInfo>()
        imageBoxDataSource.getImageBoxDtoList(pageId).collect() { imageBoxDtoList ->
            for (imageBoxDto in imageBoxDtoList) {
                imageBoxInfoList.add(ImageBoxInfo(imageBoxDto.id, imageBoxDto.boxData, imageBoxDto.image))
            }
            emit(imageBoxInfoList)
        }
    }

    override suspend fun updateImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<Boolean> = flow {
        imageBoxDataSource.updateImageBoxDto(ImageBoxDto(imageBoxInfo.id, pageId, imageBoxInfo.boxData, imageBoxInfo.image)).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteImageBoxInfo(id: String): Flow<Boolean> = flow {
        imageBoxDataSource.deleteImageBoxDto(id).collect() { result ->
            emit(result)
        }
    }
}