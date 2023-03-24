package com.tntt.repo

import com.tntt.model.PageInfo

interface PageRepository {
    fun createPageInfo(bookId: String, pageInfo: PageInfo): String
    fun getPageInfo(bookId: String, pageOrder: Int): PageInfo
    fun getPageInfoList(bookId: String): List<PageInfo>
    fun updatePageInfoList(bookId: String, pageInfoList: List<PageInfo>): Boolean
}