package com.tntt.model

data class TextBoxInfo(
    val id: String,
    val text: String,
    val fontSizeRatio: Float,
    val boxData: BoxData,
) {
    constructor() : this(
        id = "",
        text = "",
        fontSizeRatio = 0f,
        boxData = BoxData(0f, 0f , 0f, 0f)
    )
}