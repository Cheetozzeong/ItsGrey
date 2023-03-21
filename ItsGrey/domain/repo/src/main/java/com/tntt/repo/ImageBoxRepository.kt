package com.tntt.repo

import com.tntt.model.ImageBoxInfo

interface ImageBoxRepository {
    fun getImageBoxInfo(pageId: String): ImageBoxInfo
}