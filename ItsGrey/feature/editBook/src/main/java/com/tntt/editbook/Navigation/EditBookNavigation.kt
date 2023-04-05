package com.tntt.editbook.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editbook.EditBookPageRoute

internal const val pageIdArg = "pageId"

const val editBookPageRoute = "editBookPage_route"
private const val editBookPageGraphRoutePattern = "editBookPage_route"

fun NavController.navigateToEditBookPage(pageId: String) {
    val encodedId = Uri.encode(pageId)
    this.navigate("$editBookPageGraphRoutePattern/$encodedId")
}

fun NavGraphBuilder.editBookPageScreen(
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onNewPageClick: () -> Unit,
) {
    composable(
//        route = "$editBookPageRoute/{pageIdArg}",
//        arguments = listOf(
//            navArgument(pageIdArg) {type = NavType.StringType}
//        )
        route = editBookPageRoute
    ) {
        EditBookPageRoute(
            onBackClick = onBackClick,
            onViewerClick = onViewerClick,
            onNewPageClick = onNewPageClick,
        )
    }
}