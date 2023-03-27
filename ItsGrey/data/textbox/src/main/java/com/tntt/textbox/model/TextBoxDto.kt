package com.tntt.textbox.model

import com.tntt.model.BoxData
import com.tntt.model.BoxState

data class TextBoxDto(
    var id: String,
    val pageId: String,
    val text: String,
    val fontSizeRatio: Float,
    val boxData: BoxData,
)