package com.tntt.repo

import com.tntt.model.ImageBoxInfo

interface ImageBoxRepository {
    fun createImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): String
    fun getImageBoxInfo(pageId: String): ImageBoxInfo
    fun updateImageBoxInfo(pageId: String, imageBoxInfo: ImageBoxInfo): Boolean
    fun deleteImageBoxInfo(id: String):  Boolean
}