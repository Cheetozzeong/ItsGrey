package com.tntt.book.model

import sun.jvm.hotspot.utilities.BitMap
import java.util.Date

data class ContentState(
    val offsetX: Double,
    val offsetY: Double,
    val size: Double,
    val text: String
)

data class ImageState(
    val offsetX: Double,
    val offsetY: Double,
    val size: Double,
    val dpi: Any,
    val imageBitmap: BitMap,
)

data class Thumbnail(
    val contents: List<ContentState>,
    val image: List<ImageState>
)

interface Book {
    val bookId: Int
    val title: String
    val thumbnail: Thumbnail
}

data class BookForPublication(
    override val bookId: Int,
    override val title: String,
    override val thumbnail: Thumbnail,
    val publicationDate: Date
    ) : Book

data class BookForWrite(
    override val bookId: Int,
    override val title: String,
    override val thumbnail: Thumbnail,
    val recentWriteDate: Date
    ) : Book