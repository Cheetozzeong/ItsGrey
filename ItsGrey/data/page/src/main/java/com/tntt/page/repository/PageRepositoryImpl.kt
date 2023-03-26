package com.tntt.page.repository

import com.tntt.model.PageInfo
import com.tntt.page.datasource.RemotePageDataSource
import com.tntt.page.datasource.RemotePageDataSourceImpl
import com.tntt.page.model.PageDto
import com.tntt.repo.PageRepository
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val pageDataSource: RemotePageDataSource
): PageRepository {

    override fun createPageInfo(bookId: String, pageInfo: PageInfo): String {
        val pageDto = PageDto("", bookId, pageInfo.order)
        return pageDataSource.createPageDto(pageDto)
    }

    override fun getPageInfo(bookId: String, pageOrder: Int): PageInfo {
        val pageDto = pageDataSource.getPageDto(bookId, pageOrder)
        return PageInfo(pageDto.id, pageDto.order)
    }

    override fun getFirstPageInfo(bookId: String): PageInfo {
        val pageDto = pageDataSource.getFirstPageDto(bookId)
        return getPageInfo(pageDto.id, pageDto.order)
    }

    override fun getPageInfoList(bookId: String): List<PageInfo> {
        val pageInfoList = mutableListOf<PageInfo>()

        val pageDtoList = pageDataSource.getPageDtoList(bookId)
        for (pageDto in pageDtoList) {
            pageInfoList.add(PageInfo(pageDto.id, pageDto.order))
        }
        return pageInfoList
    }

    override fun updatePageInfoList(bookId: String, pageInfoList: List<PageInfo>): Boolean {
        val pageDtoList = mutableListOf<PageDto>()

        for (pageInfo in pageInfoList) {
            pageDtoList.add(PageDto(pageInfo.id, bookId, pageInfo.order))
        }
        return pageDataSource.updatePageDto(pageDtoList)
    }
}