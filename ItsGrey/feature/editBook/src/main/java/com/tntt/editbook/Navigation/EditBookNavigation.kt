package com.tntt.editbook.Navigation

import android.net.Uri
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.tntt.editbook.EditBookPageRoute

internal const val bookIdArg = "bookIdArg"
internal const val userIdArg = "userIdArg"

const val editBookPageRoute = "editBookPage_route"
private const val editBookPageGraphRoutePattern = "editBookPage_route"

fun NavController.navigateToEditBookPage(bookId: String, userId: String) {
    val encodedBookId = Uri.encode(bookId)
    val encodedUserId = Uri.encode(userId)
    this.navigate("$editBookPageGraphRoutePattern/$encodedUserId/$encodedBookId")
}

fun NavGraphBuilder.editBookPageScreen(
    onBackClick: () -> Unit,
    onViewerClick: (String) -> Unit,
//    onViewerClick: (String) -> Unit,
    onPageClick: (String) -> Unit,
    currentUserEmail: String,
//    currentBookId: String,
) {
    composable(
        route = "$editBookPageRoute/{$userIdArg}/{$bookIdArg}",
        arguments = listOf(
            navArgument(userIdArg) {type = NavType.StringType},
            navArgument(bookIdArg) {type = NavType.StringType},
        )
    ) {
        EditBookPageRoute(
            onBackClick = onBackClick,
            onViewerClick = onViewerClick,
            onPageClick = { pageId -> onPageClick(pageId)},
        )
    }
}