package com.tntt.feature.editpage

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
//import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.feature.editpage.navigation.EditPageArgs
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

    private val _imageBox = MutableStateFlow(ImageBoxInfo())
    val imageBox: StateFlow<ImageBoxInfo> = _imageBox

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
                        arrayListOf(
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
            }
        }
    }

    fun createTextBox() {
        _textBoxList.value = textBoxList.value + TextBoxInfo(
            id = UUID.randomUUID().toString(),
            text = "",
            fontSizeRatio = 0.05f,
            boxData = BoxData(0.2f, 0.6f , 0.5f, 0.3f)
        )
    }
}

sealed interface EditPageUiState {
    object Loading : EditPageUiState

    data class EditPage(
        val page: Page
    ) : EditPageUiState

    object Empty : EditPageUiState
}