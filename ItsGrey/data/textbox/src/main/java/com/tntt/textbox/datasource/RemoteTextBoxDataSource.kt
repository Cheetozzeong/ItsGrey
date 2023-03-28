package com.tntt.textbox.datasource

import com.tntt.textbox.model.TextBoxDto
import kotlinx.coroutines.flow.Flow

interface RemoteTextBoxDataSource {
    suspend fun createTextBoxDto(textBoxDto: TextBoxDto): Flow<String>
    suspend fun getTextBoxDtoList(pageId: String): Flow<List<TextBoxDto>>
    suspend fun updateTextBoxDtoList(textBoxDtoList: List<TextBoxDto>): Flow<Boolean>
    suspend fun deleteTextBoxDto(id: String): Flow<Boolean>
}