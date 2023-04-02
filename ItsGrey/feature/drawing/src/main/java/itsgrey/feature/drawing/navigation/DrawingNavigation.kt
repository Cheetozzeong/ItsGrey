package itsgrey.feature.drawing.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.*
import androidx.navigation.compose.composable
import itsgrey.feature.drawing.Sample
import kotlinx.parcelize.Parcelize


private const val drawingGraphRoutePattern = "drawingGraphRoutePattern"
const val drawingRoute = "drawing_route"
const val imageBoxIdArgs = "imageBoxId"
const val imageUriArgs = "imageUri"

fun NavController.toDrawing(imageBoxId: String, imageUri: Uri?) {
    val imageBoxIdArgs = Uri.encode(imageBoxId)
    val imageUriArgs = imageUri?.let { Uri.encode(it.toString()) } ?: ""
    Log.d("testNav uri1", "$drawingGraphRoutePattern/${imageBoxIdArgs}/${imageUriArgs}")
    Log.d("testNav", currentBackStackEntry?.destination?.route.toString())
    navigate("$drawingGraphRoutePattern/${imageBoxIdArgs}/${imageUriArgs}")
}

fun NavGraphBuilder.drawingScreen(
    onBackClick: () -> Unit
) {
    Log.d("testNav uri2", "$drawingGraphRoutePattern/${imageBoxIdArgs}/${imageUriArgs}")
    navigation(
        route = "$drawingGraphRoutePattern/${imageBoxIdArgs}/${imageUriArgs}",
        startDestination = drawingRoute
    ) {
        composable(
            route = drawingRoute,
            arguments = listOf(
                navArgument(imageBoxIdArgs) { type = NavType.StringType },
                navArgument(imageUriArgs) { type = NavType.ParcelableType(Uri::class.java) }
            )
        ) {
            val imageBoxId = it.arguments?.getString(imageBoxIdArgs)!!
            val imageUri = it.arguments?.getParcelable<Uri>(imageUriArgs)

            Sample(imageBoxId, imageUri)
        }
    }
}