package com.tntt.textbox.repository

import com.tntt.model.TextBoxInfo
import com.tntt.repo.TextBoxRepository
import com.tntt.textbox.datasource.RemoteTextBoxDataSource
import com.tntt.textbox.model.TextBoxDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TextBoxRepositoryImpl @Inject constructor(
    private val textBoxDataSource: RemoteTextBoxDataSource
) : TextBoxRepository {

    override suspend fun createTextBoxInfo(pageId: String, textBoxInfo: TextBoxInfo): Flow<String> = flow {
        textBoxDataSource.createTextBoxDto(TextBoxDto("", pageId, textBoxInfo.text, textBoxInfo.fontSizeRatio, textBoxInfo.boxData)).collect() { textBoxInfoId ->
            emit(textBoxInfoId)
        }
    }

    override suspend fun getTextBoxInfoList(pageId: String): Flow<List<TextBoxInfo>> = flow {
        textBoxDataSource.getTextBoxDtoList(pageId).collect() { textBoxDtoList ->
            val textBoxInfoList = mutableListOf<TextBoxInfo>()
            for (textBoxDto in textBoxDtoList) {
                textBoxInfoList.add(TextBoxInfo(textBoxDto.id, textBoxDto.text, textBoxDto.fontSizeRatio, textBoxDto.boxData))
            }
            emit(textBoxInfoList)
        }
    }

    override suspend fun updateTextBoxInfoList(pageId: String, textBoxInfoList: List<TextBoxInfo>): Flow<Boolean> = flow {
        val textBoxDtoList = mutableListOf<TextBoxDto>()
        for (textBoxInfo in textBoxDtoList) {
            textBoxDtoList.add(TextBoxDto(textBoxInfo.id, pageId, textBoxInfo.text, textBoxInfo.fontSizeRatio, textBoxInfo.boxData))
        }
        textBoxDataSource.updateTextBoxDtoList(textBoxDtoList).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteTextBoxInfo(id: String): Flow<Boolean> = flow {
        textBoxDataSource.deleteTextBoxDto(id).collect() { result ->
            emit(result)
        }
    }
}