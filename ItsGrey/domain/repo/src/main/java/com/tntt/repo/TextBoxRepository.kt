package com.tntt.repo

import com.tntt.model.TextBoxInfo

interface TextBoxRepository {
    fun createTextBoxInfo(pageId: String, textBoxInfo: TextBoxInfo): String
    fun getTextBoxInfoList(pageId: String): List<TextBoxInfo>
    fun updateTextBoxInfoList(pageId: String, textBoxInfoList: List<TextBoxInfo>): Boolean
    fun deleteTextBoxInfo(id: String): Boolean
}