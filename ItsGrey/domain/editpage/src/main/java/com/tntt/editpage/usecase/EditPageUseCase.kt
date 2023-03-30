package com.tntt.editpage.usecase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tntt.editpage.model.Page
import com.tntt.model.BoxData
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail
import com.tntt.repo.ImageBoxRepository
import com.tntt.repo.LayerRepository
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

    fun createImageBox(pageId: String, imageBoxInfo: ImageBoxInfo): String {
        return imageBoxRepository.createImageBoxInfo(pageId, imageBoxInfo)
    }

    fun createTextBox(pageId: String, textBoxInfo: TextBoxInfo): String {
        return textBoxRepository.createTextBoxInfo(pageId, textBoxInfo)
    }

    fun getPage(pageId: String): Flow<Page> {
        val imageBoxInfo = imageBoxRepository.getImageBoxInfo(pageId)
        val textBoxInfoList = textBoxRepository.getTextBoxInfoList(pageId)
        return flow { Page(pageId, pageRepository.getThumbnail(pageId)) }
    }

    fun savePage(page: Page): Boolean {
        return (imageBoxRepository.updateImageBoxInfo(page.id, page.thumbnail.imageBox) && textBoxRepository.updateTextBoxInfoList(page.id, page.thumbnail.textBoxList))
    }

    fun deleteImageBox(imageBoxId: String): Boolean {
        return imageBoxRepository.deleteImageBoxInfo(imageBoxId)
    }

    fun deleteTextBox(textBoxId: String): Boolean {
        return textBoxRepository.deleteTextBoxInfo(textBoxId)
    }
}