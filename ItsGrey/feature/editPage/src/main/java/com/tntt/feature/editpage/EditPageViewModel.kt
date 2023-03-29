package com.tntt.feature.editpage

import android.graphics.Bitmap
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

//    val pageState: StateFlow<EditPageUiState> =
//        editPageUseCase.getPage(pageId = pageId).map(
//            EditPageUiState::EditPage,
//        ).stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5_000),
//            initialValue = EditPageUiState.Loading
//        )

    val pageState: StateFlow<EditPageUiState> = flowOf(
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
    ).map(
        EditPageUiState::EditPage,
    ).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = EditPageUiState.Loading
    )


}

sealed interface EditPageUiState {
    object Loading : EditPageUiState

    data class EditPage(
        val page: Page
    ) : EditPageUiState

    object Empty : EditPageUiState
}