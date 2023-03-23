package com.tntt.imagebox.model

import com.tntt.model.BoxState

data class ImageBoxDto(
    var id: String,
    val pageId: String,
    val boxState: BoxState,
)