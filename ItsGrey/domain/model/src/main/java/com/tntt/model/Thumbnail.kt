package com.tntt.model

import android.graphics.Bitmap

data class Thumbnail(
    val imageBox: ImageBoxInfo,
    val image: Bitmap,
    val textBoxeList: List<TextBoxInfo>,
)