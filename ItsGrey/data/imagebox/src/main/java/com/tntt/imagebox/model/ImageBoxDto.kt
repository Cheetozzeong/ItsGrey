package com.tntt.imagebox.model

import com.tntt.model.BoxData

data class ImageBoxDto(
    var id: String,
    val pageId: String,
    val boxData: BoxData,
)