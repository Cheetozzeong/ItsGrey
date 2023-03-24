package com.tntt.imagebox.datasource

import com.tntt.imagebox.model.ImageBoxDto

interface RemoteImageBoxDataSource {

    fun createImageBoxDto(imageBoxDto: ImageBoxDto): String
    fun getImageBoxDto(pageId: String): ImageBoxDto
    fun updateImageBoxDto(imageBoxDto: ImageBoxDto): Boolean
    fun deleteImageBoxDto(id: String):  Boolean
}