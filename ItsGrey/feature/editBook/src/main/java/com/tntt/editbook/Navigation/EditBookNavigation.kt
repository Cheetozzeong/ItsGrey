package com.tntt.editbook.Navigation

import android.net.Uri
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.tntt.editbook.EditBookPageRoute

internal const val bookIdArg = "ff18f9d3-4353-4fa5-9d48-66d45003913d"
internal const val userIdArg = "userId"

const val editBookPageRoute = "editBookPage_route"
private const val editBookPageGraphRoutePattern = "editBookPage_route"

fun NavController.navigateToEditBookPage(bookId: String) {
    val encodedId = Uri.encode(bookId)
    this.navigate("$editBookPageGraphRoutePattern/$encodedId")
}

fun NavGraphBuilder.editBookPageScreen(
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onNewPageClick: () -> Unit,
    currentUserEmail: String,
//    currentBookId: String,
) {
    composable(
        route = editBookPageRoute,
//        route = "$editBookPageRoute/{$userIdArg}/{$bookIdArg}",
        arguments = listOf(
            navArgument(userIdArg) {type = NavType.StringType},
//            navArgument(bookIdArg) {type = NavType.StringType},
        )
    ) {
        it.arguments?.apply {
            putString(userIdArg, currentUserEmail)
//            putString(bookIdArg, currentBookId)
        }
        EditBookPageRoute(
            onBackClick = onBackClick,
            onViewerClick = onViewerClick,
            onNewPageClick = onNewPageClick,
        )
    }
}