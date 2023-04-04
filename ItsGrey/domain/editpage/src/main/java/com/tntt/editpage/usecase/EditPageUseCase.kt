package com.tntt.editpage.usecase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tntt.editpage.model.Page
import com.tntt.model.BoxData
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail
import com.tntt.repo.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EditPageUseCase @Inject constructor(
    private val pageRepository: PageRepository,
    private val imageBoxRepository: ImageBoxRepository,
    private val textBoxRepository: TextBoxRepository,
    private val layerRepository: LayerRepository,
    private val drawingRepository: DrawingRepository,
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

    fun getImageBoxList(pageId: String): Flow<List<ImageBoxInfo>> = flow {
        imageBoxRepository.getImageBoxInfoList(pageId).collect() { imageBoxList ->
            emit(imageBoxList)
        }
    }

    fun getTextBoxList(pageId: String): Flow<List<TextBoxInfo>> = flow {
        textBoxRepository.getTextBoxInfoList(pageId).collect() { textBoxList ->
            emit(textBoxList)
        }
    }

    fun saveImageBox(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<Boolean> = flow {
        imageBoxRepository.updateImageBoxInfo(pageId, imageBoxInfo).collect() { result ->
            emit(result)
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
        layerRepository.deleteLayerInfoList(imageBoxId).collect() { deleteLayerResult ->
            drawingRepository.deleteDrawingInfo(imageBoxId).collect() { deleteDrawingResult ->
                imageBoxRepository.deleteImageBoxInfo(imageBoxId).collect() { deleteImageBoxResult ->
                    emit(deleteLayerResult && deleteDrawingResult && deleteImageBoxResult)
                }
            }
        }
    }

    fun deleteTextBox(textBoxId: String): Flow<Boolean> = flow {
        textBoxRepository.deleteTextBoxInfo(textBoxId).collect() { result ->
            emit(result)
        }
    }
}