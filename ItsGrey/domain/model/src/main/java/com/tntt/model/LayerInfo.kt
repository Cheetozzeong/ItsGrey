package com.tntt.model

import android.graphics.Bitmap

data class LayerInfo(
    var id: String,
    val order: Int,
    val bitmap: Bitmap,
)