package com.tntt.feature.editpage

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
//import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
//import com.tntt.feature.editpage.navigation.EditPageArgs
import com.tntt.model.BoxData
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditPageViewModel @Inject constructor(
    editPageUseCase: EditPageUseCase,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

//    private val pageArgs: EditPageArgs = EditPageArgs(savedStateHandle, stringDecoder)
//
//    val pageId = pageArgs.pageId

    private val _textBoxList = MutableStateFlow(listOf<TextBoxInfo>())
    val textBoxList: StateFlow<List<TextBoxInfo>> = _textBoxList

    private val _imageBox = MutableStateFlow(listOf(ImageBoxInfo()))
    val imageBox: StateFlow<List<ImageBoxInfo>> = _imageBox

    private val _image = MutableStateFlow(ImageBitmap(10, 10, ImageBitmapConfig.Argb8888))
    val image: StateFlow<ImageBitmap> = _image

    private val _selectedBoxId = MutableStateFlow("")
    val selectedBoxId: StateFlow<String> = _selectedBoxId

    init {
        viewModelScope.launch {
            val page = flowOf(
                Page(
                    id = "fakePageId",
                    thumbnail = Thumbnail(
                        ImageBoxInfo(
                            id = "image",
                            boxData = BoxData(
                                offsetRatioX = 0.2f,
                                offsetRatioY = 0.2f,
                                widthRatio = 0.5f,
                                heightRatio = 0.3f
                            )
                        ),
                        image = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888),
                        listOf(
                            TextBoxInfo(
                                id = "abc",
                                text = "ABC",
                                fontSizeRatio = 0.05f,
                                boxData = BoxData(
                                    offsetRatioX = 0.2f,
                                    offsetRatioY = 0.2f,
                                    widthRatio = 0.5f,
                                    heightRatio = 0.3f
                                )
                            ),
                            TextBoxInfo(
                                id = "def",
                                text = "DEF",
                                fontSizeRatio = 0.05f,
                                boxData = BoxData(
                                    offsetRatioX = 0.2f,
                                    offsetRatioY = 0.4f,
                                    widthRatio = 0.5f,
                                    heightRatio = 0.3f
                                )
                            ),
                            TextBoxInfo(
                                id = "ghi",
                                text = "GHI",
                                fontSizeRatio = 0.05f,
                                boxData = BoxData(
                                    offsetRatioX = 0.2f,
                                    offsetRatioY = 0.6f,
                                    widthRatio = 0.5f,
                                    heightRatio = 0.3f
                                )
                            )
                        )
                    )
                )
            )
            page.collect {
                _textBoxList.value = it.thumbnail.textBoxList
                _imageBox.value = listOf(it.thumbnail.imageBox)
            }
        }
    }

    fun createTextBox() {
        val newBox = TextBoxInfo(
            id = UUID.randomUUID().toString(),
            text = "",
            fontSizeRatio = 0.05f,
            boxData = BoxData(0.2f, 0.6f , 0.5f, 0.3f)
        )
        _textBoxList.value = textBoxList.value + newBox
        _selectedBoxId.value = newBox.id
    }

    fun createImageBox() {
        val newBox = ImageBoxInfo(
            id = UUID.randomUUID().toString(),
            boxData = BoxData(0.2f, 0.2f , 0.5f, 0.33f)
        )
        _imageBox.value = listOf(
            newBox
        )
        _image.value = ImageBitmap(30, 30)
        _selectedBoxId.value = newBox.id
    }

    fun updateTextBox(textBoxInfo: TextBoxInfo) {
        val currentList = _textBoxList.value.toMutableList()
        val indexToUpdate = currentList.indexOfFirst { it.id == textBoxInfo.id }

        if(indexToUpdate != -1) {
            currentList[indexToUpdate] = textBoxInfo
            _textBoxList.value = currentList
        }
    }

    fun updateImageBox(imageBoxInfo: ImageBoxInfo) {
        _imageBox.value = listOf(imageBoxInfo)
    }

    fun deleteBox(boxId: String) {
        if(imageBox.value.isNotEmpty() && imageBox.value[0].id == boxId) {
            _imageBox.value = emptyList()
        }
        else {
            val curTextBoxList = textBoxList.value.toMutableList()
            val indexToDelete = curTextBoxList.indexOfFirst { it.id == boxId }

            curTextBoxList.removeAt(indexToDelete)
            _textBoxList.value = curTextBoxList
        }
    }

    fun onBoxSelected(boxId: String) {
        _selectedBoxId.value = boxId
    }
}