package com.tntt.page.datasource

import com.tntt.page.model.PageDto

interface RemotePageDataSource {
    fun createPageDto(pageDto: PageDto): String
    fun getPageDto(bookId: String, pageOrder: Int): PageDto
    fun getPageDtoList(bookId: String): List<PageDto>
    fun updatePageDto(pageDtoList: List<PageDto>): Boolean
}