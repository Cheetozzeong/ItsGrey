package com.tntt.book.model

import sun.jvm.hotspot.utilities.BitMap
import java.util.Date

data class ContentState(
    val offsetX: Float,
    val offsetY: Float,
    val size: Float,
    val text: String
)

data class ImageState(
    val offsetX: Float,
    val offsetY: Float,
    val size: Float,
    val dpi: Any,
    val imageBitmap: BitMap,
)

data class Thumbnail(
    val contents: List<ContentState>,
    val image: ImageState
)

data class BookForPublication(
    val bookId: String,
    val title: String,
    val thumbnail: Thumbnail,
    val publicationDate: Date
)

data class BookForWrite(
    val bookId: String,
    val title: String,
    val thumbnail: Thumbnail,
    val recentWriteDate: Date
)
