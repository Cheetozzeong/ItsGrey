package itsgrey.feature.drawing.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import itsgrey.feature.drawing.Sample

private const val drawingGraphRoutePattern = "drawingGraphRoutePattern"
const val drawingRoute = "drawing_route"
const val imageBoxIdArgs = "imageBoxId"
const val imageUriArgs = "imageUri"

fun NavController.toDrawing(imageBoxId: String, imageUri: Uri?) {
    val encodedImageBoxId = Uri.encode(imageBoxId)
    val encodedImageUri = imageUri.let{ Uri.encode(imageUri.toString()) }

    val route = if(imageUri == null) "$drawingGraphRoutePattern/$encodedImageBoxId"
                else "$drawingGraphRoutePattern/$encodedImageBoxId?$imageUriArgs=$encodedImageUri"

    navigate(route)
}

fun NavGraphBuilder.drawingScreen(
    onBackClick: () -> Unit
) {
    navigation(
        route = "$drawingGraphRoutePattern/{$imageBoxIdArgs}?$imageUriArgs={$imageUriArgs}",
        startDestination = drawingRoute,
        arguments = listOf(
            navArgument(imageBoxIdArgs) { type = NavType.StringType },
            navArgument(imageUriArgs) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        composable(
            route = drawingRoute,
        ) {
            Sample()
        }
    }


//    Log.d("testNav uri2", "$drawingGraphRoutePattern/${imageBoxIdArgs}/${imageUriArgs}")
//    navigation(
//        route = "$drawingGraphRoutePattern/${imageBoxIdArgs}/${imageUriArgs}",
//        startDestination = drawingRoute
//    ) {
//
//    }
}