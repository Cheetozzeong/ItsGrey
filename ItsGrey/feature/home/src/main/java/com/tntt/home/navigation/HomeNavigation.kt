package com.tntt.home.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tntt.home.HomePageRoute

internal const val pageIdArg = "pageId"

const val homePageRoute = "homePage_route"
private const val homePageGraphRoutePattern = "homePage_route"

fun NavController.navigateToHomePage(pageId: String) {
    val encodedId = Uri.encode(pageId)
    this.navigate("$homePageGraphRoutePattern/$encodedId")
}

fun NavGraphBuilder.homePageScreen(
    onNewButtonClick: () -> Unit,
    onThumbnailClick: (String) -> Unit,
) {
    composable(
//        route = "$homePageRoute/{$pageIdArg}",
//        arguments = listOf(
//            navArgument(pageIdArg) {type = NavType.StringType}
//        )
    route = homePageRoute
    ) {
        HomePageRoute(
            onNewButtonClick = onNewButtonClick,
            onThumbnailClick = onThumbnailClick
        )
    }
}