package com.tntt.imagebox.repository

import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.datasource.RemoteImageBoxDataSourceImpl
import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.ImageBoxInfo
import com.tntt.repo.ImageBoxRepository

class ImageBoxRepositoryImpl : ImageBoxRepository {

    val imageBoxDataSource: RemoteImageBoxDataSource by lazy { RemoteImageBoxDataSourceImpl }

    override fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): String {
        val imageBoxDto = ImageBoxDto("", pageId, imageBoxInfo.boxState)
        return imageBoxDataSource.createImageBoxDto(imageBoxDto)
    }

    override fun getImageBoxInfo(pageId: String): ImageBoxInfo {
        val imageBoxDto = imageBoxDataSource.getImageBoxDto(pageId)
        return ImageBoxInfo(imageBoxDto.id, imageBoxDto.boxState)
    }

    override fun updateImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Boolean {
        return imageBoxDataSource.updateImageBoxDto(ImageBoxDto(imageBoxInfo.id, pageId, imageBoxInfo.boxState))
    }

    override fun deleteImageBoxInfo(id: String): Boolean {
        return imageBoxDataSource.deleteImageBoxDto(id)
    }
}