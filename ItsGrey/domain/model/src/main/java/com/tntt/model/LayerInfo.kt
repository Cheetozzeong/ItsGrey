package com.tntt.model

import android.graphics.Bitmap

data class LayerInfo(
    var id: String,
    val order: Long,
    val bitmap: Bitmap,
)