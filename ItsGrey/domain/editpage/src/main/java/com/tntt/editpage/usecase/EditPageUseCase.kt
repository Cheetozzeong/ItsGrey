package com.tntt.editpage.usecase

import com.tntt.editpage.model.Page
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.LayerRepository
import com.tntt.repo.PageRepository
import com.tntt.repo.TextBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EditPageUseCase @Inject constructor(
    private val pageRepository: PageRepository,
    private val imageBoxRepository: ImageBoxRepository,
    private val textBoxRepository: TextBoxRepository,
){

    fun createImageBox(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<ImageBoxInfo> = flow {
        imageBoxRepository.createImageBoxInfo(pageId, imageBoxInfo).collect() { imageBoxInfo ->
            emit(imageBoxInfo)
        }
    }

    fun createTextBox(pageId: String, textBoxInfo: TextBoxInfo): Flow<TextBoxInfo> = flow {
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
                if(page.thumbnail.imageBox != null)
                    imageBoxRepository.updateImageBoxInfo(page.id, page.thumbnail.imageBox!!).collect() { updateImageBoxResult ->
                        result = result && updateImageBoxResult
                    }
                emit(result)
            }
    }

    fun deleteImageBox(imageBoxId: String): Flow<Boolean> = flow {
        imageBoxRepository.deleteImageBoxInfo(imageBoxId).collect() { result ->
            emit(result)
        }
    }

    fun deleteTextBox(textBoxId: String): Flow<Boolean> = flow {
        textBoxRepository.deleteTextBoxInfo(textBoxId).collect() { result ->
            emit(result)
        }
    }
}