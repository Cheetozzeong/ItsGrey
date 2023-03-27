package com.tntt.textbox.datasource

import com.tntt.textbox.model.TextBoxDto

interface RemoteTextBoxDataSource {
    fun createTextBoxDto(textBoxDto: TextBoxDto): String
    fun getTextBoxDtoList(pageId: String): List<TextBoxDto>
    fun updateTextBoxDtoList(textBoxDtoList: List<TextBoxDto>): Boolean
    fun deleteTextBoxDto(id: String): Boolean
}