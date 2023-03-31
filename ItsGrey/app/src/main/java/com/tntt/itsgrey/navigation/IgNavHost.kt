package com.tntt.itsgrey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tntt.feature.editpage.navigation.editPageRoute
import com.tntt.feature.editpage.navigation.editPageScreen

@Composable
fun IgNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = editPageRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        editPageScreen(
            onBackClick = {},
            onImageClick = {}
        )
    }
}