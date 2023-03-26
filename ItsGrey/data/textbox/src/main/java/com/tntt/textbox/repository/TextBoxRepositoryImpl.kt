package com.tntt.textbox.repository

import com.tntt.model.TextBoxInfo
import com.tntt.repo.TextBoxRepository
import com.tntt.textbox.datasource.RemoteTextBoxDataSource
import com.tntt.textbox.datasource.RemoteTextBoxDataSourceImpl
import com.tntt.textbox.model.TextBoxDto
import javax.inject.Inject

class TextBoxRepositoryImpl @Inject constructor(
    private val textBoxDataSource: RemoteTextBoxDataSource
) : TextBoxRepository {

    override fun createTextBoxInfo(pageId: String, textBoxInfo: TextBoxInfo): String {
        return textBoxDataSource.createTextBoxDto(TextBoxDto("", pageId, textBoxInfo.text, textBoxInfo.fontSizeRatio, textBoxInfo.boxState))
    }

    override fun getTextBoxInfoList(pageId: String): List<TextBoxInfo> {
        val textBoxDtoList = textBoxDataSource.getTextBoxDtoList(pageId)

        val textBoxInfoList = mutableListOf<TextBoxInfo>()
        for (textBoxDto in textBoxDtoList) {
            textBoxInfoList.add(TextBoxInfo(textBoxDto.id, textBoxDto.text, textBoxDto.fontSizeRatio, textBoxDto.boxState))
        }
        return textBoxInfoList
    }

    override fun updateTextBoxInfoList(pageId: String, textBoxInfoList: List<TextBoxInfo>): Boolean {
        val textBoxDtoList = mutableListOf<TextBoxDto>()
        for (textBoxInfo in textBoxDtoList) {
            textBoxDtoList.add(TextBoxDto(textBoxInfo.id, pageId, textBoxInfo.text, textBoxInfo.fontSizeRatio, textBoxInfo.boxState))
        }
        return textBoxDataSource.updateTextBoxDtoList(textBoxDtoList)
    }

    override fun deleteTextBoxInfo(id: String): Boolean {
        return textBoxDataSource.deleteTextBoxDto(id)
    }
}