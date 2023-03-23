package com.tntt.imagebox.model

data class ImageBoxDto(
    var id: String,
    val pageId: String,
    val imageRatioX: Int,
    val imageRatioY: Int,
)