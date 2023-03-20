package com.tntt.drawing.model

import android.graphics.Bitmap

data class Layer(val id: String,
                 val bitmap: Bitmap,
                 val order: Int)