package com.tntt.model

import java.util.UUID

data class ImageBoxInfo(
    val id: String,
    val boxData: BoxData,
) {
    constructor() : this(
        id = UUID.randomUUID().toString(),
        boxData = BoxData(0.5f, 0.5f, 0.5f, 0.5f)
    )
}