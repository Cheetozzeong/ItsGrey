package com.tntt.model

import java.util.*

data class DrawingInfo(
    var id: String,
    val penSizeList: List<Int>,
    val eraserSizeList: List<Int>,
    var penColor: String,
    val recentColorList: List<String>,
) {
    constructor() : this(
        id = UUID.randomUUID().toString(),
        penSizeList = listOf(8, 12, 20),
        eraserSizeList = listOf(8, 12, 20),
        penColor = "000000",
        recentColorList = listOf()
    )
}