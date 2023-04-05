package com.tntt.editpage.usecase

import android.util.Log
import com.tntt.editpage.model.Page
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.PageRepository
import com.tntt.repo.TextBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EditPageUseCase @Inject constructor(
    private val pageRepository: PageRepository,
    private val imageBoxRepository: ImageBoxRepository,
    private val textBoxRepository: TextBoxRepository,
){

    fun createImageBox(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<ImageBoxInfo> = flow {
        Log.d("function test","createImageBox")
        imageBoxRepository.createImageBoxInfo(pageId, imageBoxInfo).collect() { imageBoxInfo ->
            emit(imageBoxInfo)
        }
    }

    fun createTextBox(pageId: String, textBoxInfo: TextBoxInfo): Flow<TextBoxInfo> = flow {
        Log.d("function test","createTextBox")
        textBoxRepository.createTextBoxInfo(pageId, textBoxInfo).collect() { textBoxInfo ->
            emit(textBoxInfo)
        }
    }

    fun getPage(pageId: String): Flow<Page> = flow {
        pageRepository.getThumbnail(pageId).collect() { thumbnail ->
            emit(Page(pageId, thumbnail))
        }
    }

    fun savePage(page: Page): Flow<Boolean> = flow {
            textBoxRepository.updateTextBoxInfoList(page.id, page.thumbnail.textBoxList).collect() { updateTextBoxResult ->
                var result = updateTextBoxResult
                val imageBoxList = page.thumbnail.imageBoxList
                for (imageBoxInfo in imageBoxList) {
                    imageBoxRepository.updateImageBoxInfo(page.id, imageBoxInfo)
                        .collect() { updateImageBoxResult ->
                            result = result && updateImageBoxResult
                        }
                }
                emit(result)
            }
    }

    fun deleteImageBox(imageBoxId: String): Flow<Boolean> = flow {
        Log.d("function test","deleteTextBox")
        imageBoxRepository.deleteImageBoxInfo(imageBoxId).collect() { result ->
            emit(result)
        }
    }

    fun deleteTextBox(textBoxId: String): Flow<Boolean> = flow {
        Log.d("function test","deleteTextBox")
        textBoxRepository.deleteTextBoxInfo(textBoxId).collect() { result ->
            emit(result)
        }
    }
}