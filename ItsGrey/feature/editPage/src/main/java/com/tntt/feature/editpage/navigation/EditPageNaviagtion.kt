package com.tntt.feature.editpage.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.tntt.feature.editpage.EditPageRoute

internal const val pageIdArg = "pageId"

const val editPageGraphRoutePattern = "editPage_graph"
const val editPageRoute = "editPage_route"

fun NavController.navigateToEditPage(pageId: String) {
    val encodedId = Uri.encode(pageId)
    this.navigate("$editPageGraphRoutePattern/$encodedId")
}

fun NavGraphBuilder.editPageScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onImageClick: (String, Uri?) -> Unit,
) {
    navigation(
        route = "$editPageGraphRoutePattern/{$pageIdArg}",
        startDestination = editPageRoute
    ) {
        composable(
            route = editPageRoute,
            arguments = listOf(
                navArgument(pageIdArg) {type = NavType.StringType}
            )
        ) {
            EditPageRoute(
                onImageToDrawClick = onImageClick,
                onBackClick = onBackClick
            )
        }
    }
}
