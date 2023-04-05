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

    fun createImageBox(pageId: String, imageBoxInfo: ImageBoxInfo): Flow<String> = flow {
        imageBoxRepository.createImageBoxInfo(pageId, imageBoxInfo).collect() { imageBoxId ->
            emit(imageBoxId)
        }
    }

    fun createTextBox(pageId: String, textBoxInfo: TextBoxInfo): Flow<String> = flow {
        textBoxRepository.createTextBoxInfo(pageId, textBoxInfo).collect() { textBoxId ->
            emit(textBoxId)
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

    fun savePage(page: Page): Flow<Boolean> = flow {
        textBoxRepository.updateTextBoxInfoList(page.id, page.thumbnail.textBoxList).collect() { updateTextBoxResult ->
            imageBoxRepository.updateImageBoxInfoList(page.id, page.thumbnail.imageBoxList).collect() { updateImageBoxResult ->
                emit(updateTextBoxResult && updateImageBoxResult)
            }
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