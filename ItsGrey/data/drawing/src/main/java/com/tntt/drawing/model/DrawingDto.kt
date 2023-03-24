package com.tntt.drawing.model

data class DrawingDto(
    var id: String,
    var imageBoxId: String,
    val penColor: String,
    val recentColors: List<String>,
)