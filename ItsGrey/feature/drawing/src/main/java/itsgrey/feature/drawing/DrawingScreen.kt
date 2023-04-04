package itsgrey.feature.drawing

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.model.LayerInfo
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.SketchbookController
import io.getstream.sketchbook.rememberSketchbookController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingRoute(
    viewModel: DrawingViewModel = hiltViewModel()
) {

    val layerList by viewModel.layerList.collectAsStateWithLifecycle()
    val drawingInfo by viewModel.drawingInfo.collectAsStateWithLifecycle()
    val aspectRatio by viewModel.aspectRatio.collectAsStateWithLifecycle()

    val selectedTool by viewModel.selectedTool.collectAsStateWithLifecycle()
    val selectedLayerOrder by viewModel.selectedLayer.collectAsStateWithLifecycle()

    val colorPaintController = rememberSketchbookController()
    LaunchedEffect(Unit) {
        colorPaintController.setImageBitmap(layerList[1].bitmap.asImageBitmap())
    }

    Scaffold(
        modifier = Modifier
            .pointerInput(selectedTool) {
                if (selectedTool == DrawingToolLabel.ColorPicker) {
                    detectTapGestures { viewModel.selectTool(DrawingToolLabel.None) }
                }
            },
        topBar = { DrawingTopAppBar(colorPaintController) },
    ) { paddingValues ->

        var colorPickerPosition by remember { mutableStateOf(Offset.Zero) }

        Row(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .width(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SideToolBar(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    sketchController = colorPaintController,
                    onSelectTool = viewModel::selectTool,
                    onGloballyPositioned = { colorPickerPosition = it.boundsInRoot().topRight }
                )

                Box(
                    Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .background(Color.Black)
                )

                LayerSection(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    aspectRatio = aspectRatio,
                    layerList = layerList,
                    selectedLayerOrder = selectedLayerOrder
                )
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                SketchScreen(
                    modifier = Modifier
                        .aspectRatio(aspectRatio)
                        .align(Alignment.Center)
                    ,
                    layerList = layerList,
                    colorPaintController = colorPaintController,
                    onDrawPath = viewModel::updateBitmap
                )
            }
        }

        if(selectedTool == DrawingToolLabel.ColorPicker) {
            ColorPicker(
                currentColor =  colorPaintController.currentPaintColor.value,
                offset = colorPickerPosition,
                setSelectedColor = { colorPaintController.setPaintColor(Color(it)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawingTopAppBar(sketchController: SketchbookController) {

    IgTopAppBar(
        modifier = Modifier,
        title = "",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "뒤로가기",
        actions = {
            UndoButton {sketchController.undo()}
            RedoButton {sketchController.redo()}
            SaveButton()
        }
    )
}

@Composable
private fun SideToolBar(
    modifier: Modifier,
    sketchController: SketchbookController,
    onSelectTool: (DrawingToolLabel) -> Unit,
    onGloballyPositioned: (LayoutCoordinates) -> Unit
) {

    Column(
        modifier
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IgIconButton(
            modifier = Modifier,
            onClick = { sketchController.setEraseMode(false) },
            icon = {
                Icon(
                    imageVector = IgIcons.Brush,
                    contentDescription = "brushButton",
                    tint = toggleTint(!sketchController.isEraseMode.value)
                )
            }
        )

        IgIconButton(
            onClick = { sketchController.toggleEraseMode() },
            icon = {
                Icon(
                    painter = painterResource(id = IgIcons.Eraser),
                    contentDescription = "EraserButton",
                    tint = toggleTint(sketchController.isEraseMode.value)
                )
            }
        )
        
        Spacer(modifier = Modifier.padding(10.dp))
        
        ColorPickerButton(
            currentColor = sketchController.currentPaintColor.value,
            onClick = { onSelectTool(it) },
            onGloballyPositioned = { onGloballyPositioned(it) }
        )
    }
}

@Composable
private fun SketchScreen(
    modifier: Modifier,
    layerList: List<LayerInfo>,
    colorPaintController: SketchbookController,
    onDrawPath: (Bitmap) -> Unit
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawImage(layerList[0].bitmap.asImageBitmap())
    }
    Sketchbook(
        modifier = modifier.fillMaxSize(),
        controller = colorPaintController,
        onPathListener = {
            onDrawPath(colorPaintController.getSketchbookBitmap().asAndroidBitmap())
        }
    )
}

@Composable
private fun LayerSection(
    modifier: Modifier,
    aspectRatio: Float,
    layerList: List<LayerInfo>,
    selectedLayerOrder: Int
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = layerList, key = {item: LayerInfo -> item.id}) { layerInfo ->
            Box(
                modifier = Modifier
                    .aspectRatio(aspectRatio)
                    .border(
                        width = 2.dp,
                        color = toggleTint(isSelected = layerInfo.order == selectedLayerOrder)
                    ),
            ) {
                Image(
                    bitmap = layerInfo.bitmap.asImageBitmap(),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun UndoButton(onClick: () -> Unit) {
    IgIconButton(
        onClick = { onClick() },
        icon = {
            Icon(
                imageVector = IgIcons.Undo,
                contentDescription = "Undo",
            )
        },
    )
}

@Composable
private fun RedoButton(onClick: () -> Unit) {
    IgIconButton(
        onClick = { onClick() },
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
private fun BrushButton(
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    IgIconButton(
        modifier = Modifier,
        onClick = { onClick() },
        icon = {
            Icon(
                imageVector = IgIcons.Brush,
                contentDescription = "brushButton",
                tint = toggleTint(isSelected)
            )
        }
    )
}

@Composable
private fun EraserButton(
    isSelected: Boolean,
    onClick: (DrawingToolLabel) -> Unit,
) {
    IgIconButton(
        onClick = { onClick(DrawingToolLabel.Eraser) },
        icon = {
            Icon(
                painter = painterResource(id = IgIcons.Eraser),
                contentDescription = "EraserButton",
                tint = toggleTint(isSelected)
            )
        }
    )
}

@Composable
private fun ColorPickerButton(
    currentColor: Color,
    onClick: (DrawingToolLabel) -> Unit,
    onGloballyPositioned: (LayoutCoordinates) -> Unit,
) {

    Box(
        Modifier
            .onGloballyPositioned(onGloballyPositioned)
            .width(40.dp)
            .height(40.dp)
            .clip(CircleShape)
            .background(currentColor)
            .pointerInput(Unit) {
                detectTapGestures {
                    onClick(DrawingToolLabel.ColorPicker)
                }
            }
            .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
    )
}

@Composable
private fun toggleTint(isSelected: Boolean) = if(isSelected) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSecondary