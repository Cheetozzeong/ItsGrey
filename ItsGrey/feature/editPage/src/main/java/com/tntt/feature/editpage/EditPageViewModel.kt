package com.tntt.feature.editpage

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.feature.editpage.navigation.pageIdArg
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
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val pageId: String = checkNotNull(savedStateHandle[pageIdArg])

    private val isSuccessToSave = MutableStateFlow(false)

    private val _textBoxList = MutableStateFlow(listOf<TextBoxInfo>())
    val textBoxList: StateFlow<List<TextBoxInfo>> = _textBoxList

    private val _imageBox = MutableStateFlow(listOf<ImageBoxInfo>())
    val imageBox: StateFlow<List<ImageBoxInfo>> = _imageBox

    private val _selectedBoxId = MutableStateFlow("")
    val selectedBoxId: StateFlow<String> = _selectedBoxId

    init{
        Log.d("viewModel", "init")
        getTextBoxList()
        getImageBoxList()
    }

    override fun onCleared() {
        Log.d("viewModel", "onCleared")
        super.onCleared()
    }

    private fun getTextBoxList(){
        viewModelScope.launch {
            editPageUseCase.getTextBoxList(pageId).collect() {
                _textBoxList.value = it
            }
        }
    }

    fun getImageBoxList(){
        CoroutineScope(Dispatchers.IO).launch {
            editPageUseCase.getImageBoxList(pageId).collect() {
                this.launch(Dispatchers.Main) {
                    _imageBox.value = it
                }
            }
        }
    }

    fun createTextBox() {
        val newBox = TextBoxInfo(
            id = UUID.randomUUID().toString(),
            text = "새로운 텍스트 박스",
            fontSizeRatio = 0.05f,
            boxData = BoxData(0.2f, 0.6f , 0.5f, 0.2f)
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

        viewModelScope.launch(Dispatchers.IO) {
            editPageUseCase.updateImageBox(pageId, imageBox.value).collect()
        }
    }

    fun updateImageBox(imageBoxId: String, uri: Uri) {

        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

        Log.d("check - curHeightRatio", "${_imageBox.value[0].boxData.heightRatio}")
        Log.d("check - ratio", "${(bitmap.height.toFloat() / bitmap.width.toFloat())}")
        _imageBox.value[0].boxData.heightRatio = _imageBox.value[0].boxData.heightRatio * (bitmap.height.toFloat() / bitmap.width.toFloat())
        Log.d("check - nextHeightRatio", "${_imageBox.value[0].boxData.heightRatio}")

        viewModelScope.launch(Dispatchers.Main) {
            editPageUseCase.updateImageBox(pageId, imageBox.value).collect()
        }
    }

    fun deleteBox(boxId: String) {
        viewModelScope.launch {
            if(imageBox.value.isNotEmpty() && imageBox.value[0].id == boxId) {
                _imageBox.value = emptyList()
                editPageUseCase.deleteImageBox(boxId).collect()
            }
            else {
                val curTextBoxList = textBoxList.value.toMutableList()
                val indexToDelete = curTextBoxList.indexOfFirst { it.id == boxId }

                curTextBoxList.removeAt(indexToDelete)
                _textBoxList.value = curTextBoxList
                editPageUseCase.deleteTextBox(boxId).collect()
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
        viewModelScope.launch(Dispatchers.IO) {
            editPageUseCase.savePage(page).collect()
        }
    }
}