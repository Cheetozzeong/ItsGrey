package com.tntt.drawing.model

data class ImageBox(val id: String,
                    val layers: List<Layer>,
                    val drawing: Drawing,
                    val ratioX: Int,
                    val ratioY: Int)