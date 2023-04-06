package com.tntt.viewer.Navigation

import android.net.Uri
import androidx.navigation.*
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
    navigation(
        route = "$viewerPageGraphRoutePattern/{$bookIdArg}",
        startDestination = viewerPageRoute,
    ) {
        composable(
            route = viewerPageRoute,
            arguments = listOf(
                navArgument(bookIdArg) {type = NavType.StringType},
            )
        ) {
            ViewerPageRoute(
                onBackClick = onBackClick,
            )
        }
    }
}