package com.tntt.itsgrey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tntt.feature.editpage.navigation.editPageGraphRoutePattern
import com.tntt.feature.editpage.navigation.editPageScreen
import com.tntt.home.navigation.homePageRoute
import com.tntt.home.navigation.homePageScreen
import itsgrey.feature.drawing.navigation.*

@Composable
fun IgNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    currentUserEmail: String,
    currentUserName:String,
    startDestination: String = "$homePageRoute/{userId}/{userName}"
//     startDestination: String = editPageGraphRoutePattern
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,

    ) {
        editPageScreen(
            onBackClick = {},
            onImageClick = { id, uri ->
                navController.toDrawing(id, uri)
            },
        )
        drawingScreen(
            onBackClick = {}
        )
        homePageScreen(
            onThumbnailClick = {
//                navController.navigateToBook(stirng)
                               },
            currentUserEmail = currentUserEmail,
            currentUserName = currentUserName
        )
        editBookPageScreen(
            onBackClick = {},
            onViewerClick = {},
            onNewPageClick = {},
//            onViewerClick = { id -> navController.toViewer(id) }
        )
    }
}