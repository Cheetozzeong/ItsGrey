package com.tntt.repo

import com.tntt.model.PageInfo
import com.tntt.model.Thumbnail
import kotlinx.coroutines.flow.Flow

interface PageRepository {
    suspend fun createPageInfo(bookId: String, pageInfo: PageInfo): Flow<PageInfo>
    suspend fun getPageInfo(bookId: String, pageOrder: Long): Flow<PageInfo>
    suspend fun getPageInfoList(bookId: String): Flow<List<PageInfo>>
    suspend fun getFirstPageInfo(bookId: String): Flow<PageInfo?>
    suspend fun updatePageInfoList(bookId: String, pageInfoList: List<PageInfo>): Flow<Boolean>
    suspend fun getThumbnail(pageId: String): Flow<Thumbnail>
    suspend fun hasCover(bookId: String): Flow<Boolean>
}