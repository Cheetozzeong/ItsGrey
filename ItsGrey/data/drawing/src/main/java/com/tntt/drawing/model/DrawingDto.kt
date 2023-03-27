package com.tntt.drawing.model

data class DrawingDto(
    var id: String,
    val imageBoxId: String,
    val penSizeList: List<Int>,
    val eraserSizeList: List<Int>,
    val penColor: String,
    val recentColors: List<String>,
)