package com.tntt.editbook.model

import com.tntt.model.PageInfo
import com.tntt.model.Thumbnail

data class Page(
    var pageInfo: PageInfo,
    val thumbnail: Thumbnail,
)