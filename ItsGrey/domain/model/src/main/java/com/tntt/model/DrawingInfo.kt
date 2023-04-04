package com.tntt.model

data class DrawingInfo(
    var id: String,
    val penSizeList: List<Int>,
    val eraserSizeList: List<Int>,
    val penColor: String,
    val recentColorList: List<String>,
)