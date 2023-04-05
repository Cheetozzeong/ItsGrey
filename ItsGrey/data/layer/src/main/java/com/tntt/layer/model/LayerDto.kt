package com.tntt.layer.model

import android.graphics.Bitmap
import android.net.Uri

data class LayerDto(
    var id: String,
    val imageBoxId: String,
    val order: Int,
    val url: String,
)