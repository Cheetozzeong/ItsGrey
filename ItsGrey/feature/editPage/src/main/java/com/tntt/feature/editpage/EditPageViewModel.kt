package com.tntt.feature.editpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.feature.editpage.navigation.EditPageArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EditPageViewModel @Inject constructor(
    editPageUseCase: EditPageUseCase,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

    private val pageArgs: EditPageArgs = EditPageArgs(savedStateHandle, stringDecoder)

    val pageId = pageArgs.pageId

    val pageState: StateFlow<EditPageUiState> =
//        editPageUseCase.getPage(pageId = pageId).map(
//            EditPageUiState::Page,
//        ).stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5_000),
//            initialValue = EditPageUiState.Loading
//        )


}

sealed interface EditPageUiState {
    object Loading : EditPageUiState

    data class Page(
        val page: Page
    ) : EditPageUiState

    object Empty : EditPageUiState
}