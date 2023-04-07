package com.tntt.itsgrey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tntt.editbook.Navigation.editBookPageScreen
import com.tntt.editbook.Navigation.navigateToEditBookPage
import com.tntt.feature.editpage.navigation.editPageGraphRoutePattern
import com.tntt.feature.editpage.navigation.editPageScreen
import com.tntt.feature.editpage.navigation.navigateToEditPage
import com.tntt.home.navigation.homePageRoute
import com.tntt.home.navigation.homePageScreen
import com.tntt.viewer.Navigation.navigateToViewerPage
import com.tntt.viewer.Navigation.viewerPageScreen
import itsgrey.feature.drawing.navigation.*

@Composable
fun IgNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    currentUserEmail: String,
    currentUserName:String,
    startDestination: String = "$homePageRoute/{userId}/{userName}"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,

    ) {
        editPageScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onImageClick = { id, uri ->
                navController.toDrawing(id, uri)
            },
            navController = navController
        )
        drawingScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
        homePageScreen(
            onThumbnailClickForEdit = { bookId -> navController.navigateToEditBookPage(bookId, currentUserEmail) },
            onThumbnailClickForView = { bookId -> navController.navigateToViewerPage(bookId) },
            currentUserEmail = currentUserEmail,
            currentUserName = currentUserName
        )
        editBookPageScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onViewerClick = { bookId ->
                navController.navigateToViewerPage(bookId)
            },
            onPageClick = { pageId ->
                navController.navigateToEditPage(pageId)
            },
            currentUserEmail = currentUserEmail,
        )
        viewerPageScreen(
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}