package com.tntt.model

import android.graphics.Bitmap

data class LayerInfo(
    val id: String,
    val order: Int,
    var bitmap: Bitmap,
)