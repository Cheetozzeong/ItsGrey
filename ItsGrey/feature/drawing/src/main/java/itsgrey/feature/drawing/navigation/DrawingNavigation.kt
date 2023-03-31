package itsgrey.feature.drawing.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tntt.core.common.decoder.StringDecoder
import itsgrey.feature.drawing.launchGallery

internal const val imageBoxIdArg = "imageBoxId"

internal class DrawingArgs(val imageBoxId: String) {
    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
            this(stringDecoder.decodeString(checkNotNull(savedStateHandle[imageBoxIdArg])))
}

private const val drawingGraphRoutePattern = "drawing_graph"
const val drawingRoute = "drawing_route"
const val selectImageRoute = "selectImage_route"

fun NavController.navigateToDrawingGraph(navOptions: NavOptions? = null) {
    this.navigate(drawingGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.drawingScreen(
    onBackClick: () -> Unit,
    destination: String,
) {
    navigation(
        route = drawingGraphRoutePattern,
        startDestination = destination,
    ) {
        composable(route = drawingRoute) {

        }
    }
}