package com.tntt.home.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tntt.home.HomePageRoute

internal const val userIdArg = "userId"
internal const val userNameArg = "userName"

const val homePageRoute = "home_page_route"
private const val homePageGraphRoutePattern = "home_page_route"

fun NavController.navigateToHomePage(pageId: String) {
    val encodedId = Uri.encode(pageId)
    this.navigate("$homePageGraphRoutePattern/$encodedId")
}

fun NavGraphBuilder.homePageScreen(
    onThumbnailClick: (String) -> Unit,
    currentUserEmail: String,
    currentUserName: String,
) {
    composable(
        "$homePageRoute/{$userIdArg}/{$userNameArg}",
        arguments = listOf(
            navArgument(userIdArg) {type = NavType.StringType},
            navArgument(userNameArg) {type = NavType.StringType}
        )
    ) {
        it.arguments?.apply {
            putString(userIdArg, currentUserEmail)
            putString(userNameArg, currentUserName)
        }
        HomePageRoute(
            onThumbnailClick = onThumbnailClick
        )
    }
}