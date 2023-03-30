package com.tntt.model

import android.graphics.Bitmap
import android.graphics.Color
import java.util.UUID

data class ImageBoxInfo(
    val id: String,
    val boxData: BoxData,
    var image: Bitmap,
) {
    constructor() : this(
        id = UUID.randomUUID().toString(),
        boxData = BoxData(0.2f, 0.2f, 0.5f, 0.33f),
        image = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
    )
}