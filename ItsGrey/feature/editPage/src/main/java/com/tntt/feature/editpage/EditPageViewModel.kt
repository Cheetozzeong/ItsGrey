package com.tntt.feature.editpage

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditPageViewModel @Inject constructor(
    private val editPageUseCase: EditPageUseCase,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

//    private val pageArgs: EditPageArgs = EditPageArgs(savedStateHandle, stringDecoder)
//    val pageId = pageIdArg

    val pageId = "testChiheon"

    private val _textBoxList = MutableStateFlow(listOf<TextBoxInfo>())
    val textBoxList: StateFlow<List<TextBoxInfo>> = _textBoxList

    private val _imageBox = MutableStateFlow(listOf<ImageBoxInfo>())
    val imageBox: StateFlow<List<ImageBoxInfo>> = _imageBox

    private val _selectedBoxId = MutableStateFlow("")
    val selectedBoxId: StateFlow<String> = _selectedBoxId

    init{
        getTextBoxList()
        getImageBoxList()
    }

    private fun getTextBoxList(){
        CoroutineScope(Dispatchers.IO).launch {
            editPageUseCase.getTextBoxList(pageId).collect() {
                _textBoxList.value = it
            }
        }
    }

    private fun getImageBoxList(){
        CoroutineScope(Dispatchers.IO).launch {
            editPageUseCase.getImageBoxList(pageId).collect() {
                _imageBox.value = it
            }
        }
    }

    fun createTextBox() {
        val newBox = TextBoxInfo(
            id = UUID.randomUUID().toString(),
            text = "새로운 텍스트 박스",
            fontSizeRatio = 0.05f,
            boxData = BoxData(0.2f, 0.6f , 0.5f, 0.3f)
        )
        viewModelScope.launch {
            editPageUseCase.createTextBox(
                pageId = pageId,
                textBoxInfo = newBox
            ).collect()
        }
        _textBoxList.value = textBoxList.value + newBox
        _selectedBoxId.value = newBox.id
    }

    fun createImageBox() {
        val newBox = ImageBoxInfo(
            id = UUID.randomUUID().toString(),
            boxData = BoxData(0.2f, 0.2f, 0.5f, 0.33f),
            image = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
        )
        viewModelScope.launch {
            editPageUseCase.createImageBox(
                pageId = pageId,
                imageBoxInfo = newBox
            ).collect()
        }
        newBox.image.eraseColor(Color.WHITE)
        _imageBox.value = listOf(newBox)
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
        viewModelScope.launch {
            if(imageBox.value.isNotEmpty() && imageBox.value[0].id == boxId) {
                editPageUseCase.deleteImageBox(boxId).collect()
                _imageBox.value = emptyList()
            }
            else {
                val curTextBoxList = textBoxList.value.toMutableList()
                val indexToDelete = curTextBoxList.indexOfFirst { it.id == boxId }

                editPageUseCase.deleteTextBox(boxId).collect()
                curTextBoxList.removeAt(indexToDelete)
                _textBoxList.value = curTextBoxList
            }
        }
    }

    fun onBoxSelected(boxId: String) {
        _selectedBoxId.value = boxId
    }

    fun savePage() {
        val page = Page(
            id = pageId,
            Thumbnail(
                imageBox.value,
                textBoxList.value
            )
        )
        viewModelScope.launch {
            editPageUseCase.savePage(page).collect()
        }
    }
}