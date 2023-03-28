package com.tntt.feature.editpage.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.tntt.core.common.decoder.StringDecoder

internal const val pageIdArg = "pageId"

internal class EditPageArgs(val pageId: String) {
    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
            this(stringDecoder.decodeString(checkNotNull(savedStateHandle[pageIdArg])))
}

fun NavController.navigateToEditPage(pageId: String) {
    val encodedId = Uri.encode(pageId)
    this.navigate("edit_page/$encodedId")
}
