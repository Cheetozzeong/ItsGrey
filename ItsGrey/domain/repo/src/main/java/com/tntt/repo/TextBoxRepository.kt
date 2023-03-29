package com.tntt.repo

import com.tntt.model.TextBoxInfo
import kotlinx.coroutines.flow.Flow

interface TextBoxRepository {
    suspend fun createTextBoxInfo(pageId: String, textBoxInfo: TextBoxInfo): Flow<TextBoxInfo>
    suspend fun getTextBoxInfoList(pageId: String): Flow<List<TextBoxInfo>>
    suspend fun updateTextBoxInfoList(pageId: String, textBoxInfoList: List<TextBoxInfo>): Flow<Boolean>
    suspend fun deleteTextBoxInfo(id: String): Flow<Boolean>
}