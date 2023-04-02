package com.tntt.model

data class DrawingInfo(
    var id: String,
    val penSizeList: List<Long>,
    val eraserSizeList: List<Long>,
    val penColor: String,
    val recentColorList: List<String>,
)