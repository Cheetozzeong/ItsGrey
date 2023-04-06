package com.tntt.model

import android.graphics.Bitmap
import java.util.*

data class LayerInfo(
    val id: String,
    val order: Int,
    var bitmap: Bitmap,
) {
    constructor(order: Int, bitmap: Bitmap) : this(
        id = UUID.randomUUID().toString(),
        order = order,
        bitmap = bitmap,
    )
}
