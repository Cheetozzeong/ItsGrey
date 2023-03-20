package com.tntt.repo

import com.tntt.model.TextBoxInfo

interface TextBoxRepository {
    fun getTextBoxInfo(pageId: String): TextBoxInfo
}