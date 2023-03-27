package com.tntt.layer.model

import android.graphics.Bitmap

data class LayerDto(
    var id: String,
    val imageBoxId: String,
    val order: Int,
    val bitmap: Bitmap,
)