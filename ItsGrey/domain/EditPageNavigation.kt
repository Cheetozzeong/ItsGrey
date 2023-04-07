package com.tntt.feature.editpage.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.feature.editpage.EditPageRoute

internal const val pageIdArg = "pageId"

internal class EditPageArgs(val pageId: String) {
    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
            this(stringDecoder.decodeString(checkNotNull(savedStateHandle[pageIdArg])))
}

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
        route = editPageGraphRoutePattern,
        startDestination = editPageRoute
    ) {
        composable(
//        route = "$editPageRoute/{$pageIdArg}",
//        arguments = listOf(
//            navArgument(pageIdArg) {type = NavType.StringType}
//        )
            route = editPageRoute
        ) {
            EditPageRoute(
                onImageToDrawClick = onImageClick,
            )
        }
    }
}
