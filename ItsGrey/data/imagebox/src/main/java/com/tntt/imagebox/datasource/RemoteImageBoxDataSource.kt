package com.tntt.imagebox.datasource

import com.tntt.imagebox.model.ImageBoxDto
import kotlinx.coroutines.flow.Flow

interface RemoteImageBoxDataSource {

    suspend fun createImageBoxDto(imageBoxDto: ImageBoxDto): Flow<ImageBoxDto>
    suspend fun getImageBoxDtoList(pageId: String): Flow<List<ImageBoxDto>>
    suspend fun updateImageBoxDto(imageBoxDto: ImageBoxDto): Flow<Boolean>
    suspend fun deleteImageBoxDto(id: String):  Flow<Boolean>
}