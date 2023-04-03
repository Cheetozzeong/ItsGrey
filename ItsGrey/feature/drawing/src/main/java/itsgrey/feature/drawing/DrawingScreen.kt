package itsgrey.feature.drawing

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingRoute(
    viewModel: DrawingViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier,
        topBar = { DrawingTopAppBar() },
    ) { paddingValues ->
        Row(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SideBar(
                Modifier.width(70.dp)
            )
            DrawingMainBox(
                Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawingTopAppBar() {

    IgTopAppBar(
        modifier = Modifier,
        title = "",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "뒤로가기",
        actions = {
            UndoButton()
            RedoButton()
            SaveButton()
        }
    )
}

@Composable
private fun SideBar(
    modifier: Modifier = Modifier,
) {
    
    Column(
        modifier
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BrushButton()
        EraserButton()
        
        Spacer(modifier = Modifier.padding(10.dp))
        
        ColorPickerButton()

        Spacer(modifier = Modifier.padding(20.dp))
        Box(Modifier.width(40.dp).height(2.dp).background(Color.Black))
        Spacer(modifier = Modifier.padding(20.dp))

        LayerSection(
            listOf(
                Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888),
                Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
            )
        )
    }

}

@Composable
private fun DrawingMainBox(
    modifier: Modifier = Modifier
) {

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    Box(
        modifier
            .background(MaterialTheme.colorScheme.secondary)
            .transformable(state = state)
            .drawWithContent {
                drawContent()
                drawRect(color = Color.Transparent, size = size, blendMode = BlendMode.Dst)
            }
    ) {
        Canvas(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .fillMaxSize()
                .aspectRatio(1f / 1f)
                .background(MaterialTheme.colorScheme.surface)
            ,
            onDraw = {

            }
        )
    }
}

@Composable
private fun LayerSection(
    layerList: List<Bitmap>
) {
    LazyColumn {
        items(items = layerList) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(8.dp)
            ) {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun UndoButton() {
    IgIconButton(
        onClick = { /*TODO*/ },
        icon = {
            Icon(
                imageVector = IgIcons.Undo,
                contentDescription = "Undo",
            )
        }
    )
}

@Composable
private fun RedoButton() {
    IgIconButton(
        onClick = { /*TODO*/ },
        icon = {
            Icon(
                imageVector = IgIcons.Redo,
                contentDescription = "Redo",
            )
        }
    )
}

@Composable
private fun SaveButton() {
    IgTextButton(
        onClick = { /*TODO*/ },
        text = { Text(text = "저장") }
    )
}

@Composable
private fun BrushButton() {
    IgIconButton(
        modifier = Modifier,
        onClick = {/*TODO: 붓으로 변경*/},
        icon = {
            Icon(
                imageVector = IgIcons.Brush,
                contentDescription = "brushButton"
            )
        }
    )
}

@Composable
private fun EraserButton() {
    IgIconButton(
        modifier = Modifier,
        onClick = { /*TODO: 지우개로 변경*/ },
        icon = {
            Icon(
                painter = painterResource(id = IgIcons.Eraser),
                contentDescription = "EraserButton"
            )
        }
    )
}

@Composable
private fun ColorPickerButton() {

    Box(
        Modifier
            .width(40.dp)
            .height(40.dp)
            .clip(CircleShape)
            .clickable { }
            .background(Color.Red)
    )
}