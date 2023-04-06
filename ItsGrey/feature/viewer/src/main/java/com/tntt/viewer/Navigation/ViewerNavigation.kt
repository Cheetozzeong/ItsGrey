package com.tntt.viewer.Navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tntt.viewer.ViewerPageRoute

internal const val bookIdArg = "bookId"

const val viewerPageRoute = "viewer_page_route"
private const val viewerPageGraphRoutePattern = "viewer_page_route"

fun NavController.navigateToViewerPage(bookId: String) {
    val encodedId = Uri.encode(bookId)
    this.navigate("$viewerPageGraphRoutePattern/$encodedId")
}

fun NavGraphBuilder.viewerPageScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = viewerPageRoute,
//        arguments = listOf(
//            navArgument(userIdArg) {type = NavType.StringType},
//            navArgument(userNameArg) {type = NavType.StringType}
//        )
    ) {
        ViewerPageRoute(
            onBackClick = onBackClick,
        )
    }
}