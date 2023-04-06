package itsgrey.feature.drawing

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.dialog.IgDraggableDialog
import com.tntt.designsystem.icon.IgIcons
import com.tntt.model.LayerInfo
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.SketchbookController
import io.getstream.sketchbook.rememberSketchbookController
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingRoute(
    onClickBackNav: () -> Unit,
    viewModel: DrawingViewModel = hiltViewModel()
) {

    val layerList by viewModel.layerList.collectAsStateWithLifecycle()
    val drawingInfo by viewModel.drawingInfo.collectAsStateWithLifecycle()
    val aspectRatio by viewModel.aspectRatio.collectAsStateWithLifecycle()

    val selectedTool by viewModel.selectedTool.collectAsStateWithLifecycle()
    val selectedLayerOrder by viewModel.selectedLayer.collectAsStateWithLifecycle()

    val colorPaintController = rememberSketchbookController()

    if(layerList.isNotEmpty()) {
        Scaffold(
            modifier = Modifier
                .pointerInput(selectedTool) {
                    if (selectedTool == DrawingToolLabel.ColorPicker || selectedTool == DrawingToolLabel.Resizing) {
                        detectTapGestures { viewModel.selectTool(DrawingToolLabel.None) }
                    }
                },
            topBar = {
                DrawingTopAppBar(
                    sketchController = colorPaintController,
                    onClickBackNav = {
                        onClickBackNav()
                    },
                    onClickSaveButton = {
                        viewModel.updateDrawingInfo(colorPaintController)
                        viewModel.save()
                    }
                )
            },
        ) { paddingValues ->

            LaunchedEffect(Unit) {
                colorPaintController.setImageBitmap(layerList[selectedLayerOrder].bitmap.asImageBitmap())
                colorPaintController.setPaintColor(Color("#${drawingInfo.penColor}".toColorInt()))
                colorPaintController.setPaintStrokeWidth(drawingInfo.penSizeList[0].toFloat())
            }

            var colorPickerPosition by remember { mutableStateOf(Offset.Zero) }
            var resizingDialogPosition by remember { mutableStateOf(Offset.Zero) }

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
                            .fillMaxWidth(),
                        sketchController = colorPaintController,
                        onSelectTool = viewModel::selectTool,
                        onGloballyPositionedColorPicker = { colorPickerPosition = it.boundsInRoot().topRight },
                        onGloballyPositionedResizing = { resizingDialogPosition = it.boundsInRoot().topRight }
                    )

                    Spacer(Modifier.padding(20.dp))
                    Box(
                        Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(Color.Black))
                    Spacer(Modifier.padding(20.dp))

                    LayerSection(
                        modifier = Modifier
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
            if(selectedTool == DrawingToolLabel.Resizing) {
                IgDraggableDialog(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                resizingDialogPosition.x.roundToInt() + 60,
                                resizingDialogPosition.y.roundToInt()
                            )
                        },
                    minValue = 8f,
                    maxValue = 100f,
                    initialFontSize = colorPaintController.currentPaint.strokeWidth,
                    changedFontSize = { newSize -> colorPaintController.setPaintStrokeWidth(newSize) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawingTopAppBar(
    sketchController: SketchbookController,
    onClickBackNav: () -> Unit,
    onClickSaveButton: () -> Unit
) {

    IgTopAppBar(
        modifier = Modifier,
        title = "",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "뒤로가기",
        onNavigationClick = { onClickBackNav() },
        actions = {
            UndoButton { sketchController.undo() }
            RedoButton { sketchController.redo() }
            SaveButton { onClickSaveButton() }
        }
    )
}

@Composable
private fun SideToolBar(
    modifier: Modifier,
    sketchController: SketchbookController,
    onSelectTool: (DrawingToolLabel) -> Unit,
    onGloballyPositionedColorPicker: (LayoutCoordinates) -> Unit,
    onGloballyPositionedResizing: (LayoutCoordinates) -> Unit,
) {

    var isBrushMode by remember { mutableStateOf(true) }
    var penColor by remember { mutableStateOf(sketchController.currentPaintColor.value) }

    Column(
        modifier
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BrushButton(isSelected = isBrushMode) {
            isBrushMode = true
            sketchController.setPaintColor(penColor)
        }
        EraserButton(isSelected = !isBrushMode) {
            isBrushMode = false
            penColor = sketchController.currentPaintColor.value
            sketchController.setPaintColor(Color.White)
        }
        
        Spacer(modifier = Modifier.padding(10.dp))
        
        ColorPickerButton(
            currentColor = sketchController.currentPaintColor.value,
            onClick = { onSelectTool(it) },
            onGloballyPositioned = { onGloballyPositionedColorPicker(it) }
        )

        Spacer(modifier = Modifier.padding(20.dp))

        SetPenSizeButton(
            onClick = { onSelectTool(it) },
            onGloballyPositioned = { onGloballyPositionedResizing(it) }
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
    if(layerList.isNotEmpty()) {

        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            Image(
                modifier = modifier
                    .zIndex(2f)
                    .align(Alignment.Center),
                bitmap = layerList[1].bitmap.asImageBitmap(),
                contentDescription = "",
            )
            Sketchbook(
                modifier = modifier
                    .fillMaxSize()
                    .zIndex(1f)
                ,
                controller = colorPaintController,
                onPathListener = {
                    onDrawPath(colorPaintController.getSketchbookBitmap().asAndroidBitmap())
                }
            )
        }
    }
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
        reverseLayout = true
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
private fun SaveButton(
    onClick: () -> Unit
) {
    IgTextButton(
        onClick = { onClick() },
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
fun SetPenSizeButton(
    onClick: (DrawingToolLabel) -> Unit,
    onGloballyPositioned: (LayoutCoordinates) -> Unit,
) {
    Box(
        Modifier
            .onGloballyPositioned(onGloballyPositioned)
            .width(20.dp)
            .height(20.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .pointerInput(Unit) {
                detectTapGestures {
                    onClick(DrawingToolLabel.Resizing)
                }
            }
    )
}

@Composable
private fun toggleTint(isSelected: Boolean) = if(isSelected) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSecondary