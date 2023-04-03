package com.tntt.page.datasource

import com.tntt.page.model.PageDto
import kotlinx.coroutines.flow.Flow

interface RemotePageDataSource {
    suspend fun createPageDto(pageDto: PageDto): Flow<PageDto>
    suspend fun getPageDto(bookId: String, pageOrder: Int): Flow<PageDto>
    suspend fun getFirstPageDto(bookId: String): Flow<PageDto?>
    suspend fun getPageDtoList(bookId: String): Flow<List<PageDto>>
    suspend fun updatePageDto(pageDtoList: List<PageDto>): Flow<Boolean>
    suspend fun hasCover(bookId: String): Flow<Boolean>
    suspend fun deletePageDtoList(pageIdList: List<String>): Flow<Boolean>
}