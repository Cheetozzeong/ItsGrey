package com.tntt.imagebox.repository

import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.ImageBoxInfo
import com.tntt.repo.ImageBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageBoxRepositoryImpl @Inject constructor(
    private val imageBoxDataSource: RemoteImageBoxDataSource
) : ImageBoxRepository {

    override suspend fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<String> = flow {
        val imageBoxDto = ImageBoxDto("", pageId, imageBoxInfo.boxData)
        imageBoxDataSource.createImageBoxDto(imageBoxDto).collect() { imageBoxId ->
            emit(imageBoxId)
        }
    }

    override suspend fun getImageBoxInfo(pageId: String): Flow<ImageBoxInfo?> = flow {
        imageBoxDataSource.getImageBoxDto(pageId).collect() { imageBoxDto ->
            if(imageBoxDto == null)
                emit(null)
            else
                emit(ImageBoxInfo(imageBoxDto.id, imageBoxDto.boxData))
        }
    }

    override suspend fun updateImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<Boolean> = flow {
        imageBoxDataSource.updateImageBoxDto(ImageBoxDto(imageBoxInfo.id, pageId, imageBoxInfo.boxData)).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteImageBoxInfo(id: String): Flow<Boolean> = flow {
        imageBoxDataSource.deleteImageBoxDto(id).collect() { result ->
            emit(result)
        }
    }
}