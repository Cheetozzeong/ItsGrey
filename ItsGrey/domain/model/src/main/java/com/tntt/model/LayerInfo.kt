package com.tntt.model

import android.graphics.Bitmap

data class LayerInfo(
    val id: String,
    val order: Int,
    val bitmap: Bitmap,
)