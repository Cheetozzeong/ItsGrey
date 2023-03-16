package com.tntt.page.model

data class PageDto(
    val id: String,
    val order: Int,
    val imageBoxId: String,
    val isThumbnail: Boolean,
    val contentBoxIds: ArrayList<String>)