package com.tntt.repo

import com.tntt.model.PageInfo

interface PageRepository {
    fun getPageInfo(bookId: String, pageOrder: Int): PageInfo
    fun getPagesInfo(bookId: String): List<PageInfo>
    fun createPageInfo(pageInfo: PageInfo): String
    fun updatePageInfo(pageInfo: PageInfo): PageInfo
}