package com.tntt.model

import android.graphics.Bitmap

data class Thumbnail(
    val imageBoxList: List<ImageBoxInfo>,
    val textBoxList: List<TextBoxInfo>,
)