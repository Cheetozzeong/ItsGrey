package com.tntt.editpage.model

import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo

data class Page(val id: String,
                val imageBox: ImageBoxInfo, 
                val textBoxes: List<TextBoxInfo>)