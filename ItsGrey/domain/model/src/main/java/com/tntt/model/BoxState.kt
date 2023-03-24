package com.tntt.model

data class BoxData(
    val offsetRatioX: Float,
    val offsetRatioY: Float,
    val widthRatio: Float,
    val heightRatio: Float,
    val state: BoxState = BoxState.None
)

enum class BoxState {
    None,
    Active,
    InActive,
}