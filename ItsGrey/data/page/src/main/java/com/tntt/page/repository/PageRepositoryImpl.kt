package com.tntt.page.repository

import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.model.ImageBoxInfo
import com.tntt.model.PageInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail
import com.tntt.page.datasource.RemotePageDataSource
import com.tntt.page.model.PageDto
import com.tntt.repo.PageRepository
import com.tntt.textbox.datasource.RemoteTextBoxDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PageRepositoryImpl @Inject constructor(
    private val pageDataSource: RemotePageDataSource,
    private val imageBoxDataSource: RemoteImageBoxDataSource,
    private val textBoxDataSource: RemoteTextBoxDataSource,
    private val layerDataSource: RemoteLayerDataSource,
): PageRepository {

    override suspend fun createPageInfo(bookId: String, pageInfo: PageInfo): Flow<String> = flow {
        val pageDto = PageDto("", bookId, pageInfo.order)
        pageDataSource.createPageDto(pageDto).collect() { pageDtoId ->
            emit(pageDtoId)
        }
    }

    override suspend fun getPageInfo(bookId: String, pageOrder: Int): Flow<PageInfo> = flow {
        pageDataSource.getPageDto(bookId, pageOrder).collect() { pageDto ->
            emit(PageInfo(pageDto.id, pageDto.order))
        }
    }

    override suspend fun getFirstPageInfo(bookId: String): Flow<PageInfo> = flow {
        pageDataSource.getFirstPageDto(bookId).collect() { pageDto ->
            emit(PageInfo(pageDto.id, pageDto.order))
        }
    }

    override suspend fun getPageInfoList(bookId: String): Flow<List<PageInfo>> = flow {
        val pageInfoList = mutableListOf<PageInfo>()

        pageDataSource.getPageDtoList(bookId).collect() { pageDtoList ->
            for (pageDto in pageDtoList) {
                pageInfoList.add(PageInfo(pageDto.id, pageDto.order))
            }
            emit(pageInfoList)
        }
    }

    override suspend fun updatePageInfoList(bookId: String, pageInfoList: List<PageInfo>): Flow<Boolean> = flow {
        val pageDtoList = mutableListOf<PageDto>()

        for (pageInfo in pageInfoList) {
            pageDtoList.add(PageDto(pageInfo.id, bookId, pageInfo.order))
        }
        pageDataSource.updatePageDto(pageDtoList).collect() { result ->
            emit(result)
        }
    }

    override suspend fun getThumbnail(pageId: String): Flow<Thumbnail> = flow {
        println("getThumnail(${pageId})")
        imageBoxDataSource.getImageBoxDto(pageId).collect() { imageBoxDto ->
            val imageBoxInfo = ImageBoxInfo(imageBoxDto.id, imageBoxDto.boxData)
            textBoxDataSource.getTextBoxDtoList(pageId).collect() { textBoxDtoList ->
                val textBoxInfoList = mutableListOf<TextBoxInfo>()
                for (textBoxDto in textBoxDtoList) {
                    textBoxInfoList.add(TextBoxInfo(textBoxDto.id, textBoxDto.text, textBoxDto.fontSizeRatio, textBoxDto.boxData))
                }
                layerDataSource.getSumLayer(imageBoxInfo.id).collect() { sumLayer ->
                    println("sumLayer = ${sumLayer})")
                    emit(Thumbnail(imageBoxInfo, sumLayer, textBoxInfoList))
                }
            }
        }
    }

    override suspend fun hasCover(bookId: String): Boolean {
        return pageDataSource.hasCover(bookId)
    }
}