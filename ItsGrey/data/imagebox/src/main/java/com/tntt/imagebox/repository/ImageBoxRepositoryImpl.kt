package com.tntt.imagebox.repository

import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.datasource.RemoteImageBoxDataSourceImpl
import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.ImageBoxInfo
import com.tntt.repo.ImageBoxRepository

class ImageBoxRepositoryImpl : ImageBoxRepository {

    val imageBoxDataSource: RemoteImageBoxDataSource by lazy { RemoteImageBoxDataSourceImpl }

    override fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): String {
        val id = imageBoxInfo.id
        val imageRatioX = imageBoxInfo.imageRatioX
        val imageRatioY = imageBoxInfo.imageRatioY

        val imageBoxDto: ImageBoxDto = ImageBoxDto(id, pageId, imageRatioX, imageRatioY)
        return imageBoxDataSource.createImageBoxDto(imageBoxDto)
    }

    override fun getImageBoxInfo(pageId: String): ImageBoxInfo {
        val imageBoxDto: ImageBoxDto = imageBoxDataSource.getImageBoxDto(pageId)

        val id = imageBoxDto.id
        val imageRatioX = imageBoxDto.imageRatioX
        val imageRatioY = imageBoxDto.imageRatioY
        return ImageBoxInfo(id, imageRatioX, imageRatioY, null)
    }

    override fun updateImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteImageBoxInfo(id: String): Boolean {
        TODO("Not yet implemented")
    }
}