package com.tntt.feature.editpage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editpage.model.Page
import com.tntt.feature.editpage.navigation.EditPageArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

    private val pageArgs: EditPageArgs = EditPageArgs(savedStateHandle, stringDecoder)

    val pageId = pageArgs.pageId

}

sealed interface EditPageUiState {
    object Loading : EditPageUiState

    data class EditPages(
        val page: Page
    ) : EditPageUiState

    object Empty : EditPageUiState
}