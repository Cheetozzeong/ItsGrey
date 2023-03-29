package com.tntt.model

import android.graphics.Bitmap

data class Thumbnail(
    val imageBox: ImageBoxInfo,
    val image: Bitmap,
    val textBoxList: ArrayList<TextBoxInfo>,
)