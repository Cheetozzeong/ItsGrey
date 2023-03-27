package com.tntt.repo

import com.tntt.model.PageInfo
import com.tntt.model.Thumbnail

interface PageRepository {
    fun createPageInfo(bookId: String, pageInfo: PageInfo): String
    fun getPageInfo(bookId: String, pageOrder: Int): PageInfo
    fun getFirstPageInfo(bookId: String): PageInfo
    fun getPageInfoList(bookId: String): List<PageInfo>
    fun updatePageInfoList(bookId: String, pageInfoList: List<PageInfo>): Boolean
    fun getThumbnail(pageId: String): Thumbnail
    fun hasCover(bookId: String): Boolean
}