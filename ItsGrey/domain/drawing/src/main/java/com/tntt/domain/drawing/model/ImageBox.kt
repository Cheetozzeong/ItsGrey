package com.tntt.domain.drawing.model

import com.tntt.model.DrawingInfo
import com.tntt.model.LayerInfo

data class ImageBox(
    val id: String,
    val layerList: List<LayerInfo>,
    val drawing: DrawingInfo
)