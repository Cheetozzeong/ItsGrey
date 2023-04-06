package com.tntt.model

data class BoxData(
    var offsetRatioX: Float,
    var offsetRatioY: Float,
    var widthRatio: Float,
    var heightRatio: Float,
)

enum class BoxState {
    None,
    Active,
    InActive
}