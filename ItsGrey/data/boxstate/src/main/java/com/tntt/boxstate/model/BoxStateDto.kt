package com.tntt.boxstate.model

data class BoxStateDto(
    var id: String,
    val boxId: String,
    val offsetRatioX: Float,
    val offsetRatioY: Float,
    val widthRatio: Float,
)