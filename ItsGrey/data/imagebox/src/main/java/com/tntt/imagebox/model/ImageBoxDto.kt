package com.tntt.imagebox.model

import android.graphics.Bitmap
import com.tntt.model.BoxData
import com.tntt.model.BoxState

data class ImageBoxDto(
    var id: String,
    val pageId: String,
    val boxData: BoxData,
    val url: String,
)