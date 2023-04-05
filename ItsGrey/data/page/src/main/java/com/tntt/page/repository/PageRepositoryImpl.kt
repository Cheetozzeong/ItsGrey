package com.tntt.page.repository

import android.graphics.Bitmap
import android.util.Log
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

    override suspend fun createPageInfo(bookId: String, pageInfo: PageInfo): Flow<PageInfo> = flow {
        Log.d("function test", "createPageInfo(${bookId}, ${pageInfo})")
        val pageDto = PageDto(pageInfo.id, bookId, pageInfo.order)
        pageDataSource.createPageDto(pageDto).collect() { pageDto ->
            emit(pageInfo)
        }
    }

    override suspend fun getPageInfo(bookId: String, pageOrder: Int): Flow<PageInfo> = flow {
        Log.d("function test", "getPageInfo(${bookId}, ${pageOrder})")
        pageDataSource.getPageDto(bookId, pageOrder).collect() { pageDto ->
            emit(PageInfo(pageDto.id, pageDto.order))
        }
    }

    override suspend fun getPageInfoList(bookId: String): Flow<List<PageInfo>> = flow {
        Log.d("function test", "getPageInfoList(${bookId})")
        val pageInfoList = mutableListOf<PageInfo>()
        pageDataSource.getPageDtoList(bookId).collect() { pageDtoList ->
            for (pageDto in pageDtoList) {
                pageInfoList.add(PageInfo(pageDto.id, pageDto.order))
            }
            emit(pageInfoList)
        }
    }

    override suspend fun getFirstPageInfo(bookId: String): Flow<PageInfo?> = flow {
        pageDataSource.getFirstPageDto(bookId).collect() { pageDto ->
            if(pageDto != null) {
                val pageInfo: PageInfo = PageInfo(pageDto.id, pageDto.order)
                emit(pageInfo)
            }
            else{
                emit(null)
            }
        }
    }

    override suspend fun updatePageInfoList(bookId: String, pageInfoList: List<PageInfo>): Flow<Boolean> = flow {
        Log.d("function test", "updatePageInfoList(${bookId}, ${pageInfoList})")
        val pageDtoList = mutableListOf<PageDto>()

        for (pageInfo in pageInfoList) {
            pageDtoList.add(PageDto(pageInfo.id, bookId, pageInfo.order))
        }
        pageDataSource.updatePageDto(pageDtoList).collect() { result ->
            emit(result)
        }
    }

    override suspend fun getThumbnail(pageId: String): Flow<Thumbnail> = flow {
        val imageBoxInfoList = mutableListOf<ImageBoxInfo>()
        val textBoxInfoList = mutableListOf<TextBoxInfo>()

        imageBoxDataSource.getImageBoxDtoList(pageId).collect() { imageBoxDtoList ->
            for (imageBoxDto in imageBoxDtoList) {
                layerDataSource.getImage(imageBoxDto.url).collect() { bitmap ->
                    imageBoxInfoList.add(ImageBoxInfo(imageBoxDto.id, imageBoxDto.boxData, bitmap))
                }
            }
            textBoxDataSource.getTextBoxDtoList(pageId).collect() { textBoxDtoList ->
                for (textBoxDto in textBoxDtoList) {
                    textBoxInfoList.add(TextBoxInfo(textBoxDto.id, textBoxDto.text, textBoxDto.fontSizeRatio, textBoxDto.boxData))
                }
            }
            emit(Thumbnail(imageBoxInfoList, textBoxInfoList))
        }
    }

    override suspend fun hasCover(bookId: String): Flow<Boolean> = flow {
        pageDataSource.hasCover(bookId).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deletePageInfo(pageId: String): Flow<Boolean> = flow {
        pageDataSource.deletePageDto(pageId).collect() { result ->
            emit(result)
        }
    }
}