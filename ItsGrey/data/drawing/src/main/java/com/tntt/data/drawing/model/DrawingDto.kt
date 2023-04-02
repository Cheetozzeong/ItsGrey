package com.tntt.data.drawing.model

data class DrawingDto(
    var id: String,
    val imageBoxId: String,
    val penSizeList: List<Long>,
    val eraserSizeList: List<Long>,
    val penColor: String,
    val recentColors: List<String>,
)