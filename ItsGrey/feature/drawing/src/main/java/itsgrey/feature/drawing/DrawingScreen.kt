package itsgrey.feature.drawing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.tntt.model.DrawingInfo
import com.tntt.model.LayerInfo
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.SketchbookController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingRoute(
    viewModel: DrawingViewModel = hiltViewModel()
) {

    val layerList by viewModel.layerList.collectAsStateWithLifecycle()
    val drawingInfo by viewModel.drawingInfo.collectAsStateWithLifecycle()
    val ratioX by viewModel.ratioX.collectAsStateWithLifecycle()
    val ratioY by viewModel.ratioY.collectAsStateWithLifecycle()

    val selectedTool by viewModel.selectedTool.collectAsStateWithLifecycle()

    val sketchControllerMap by viewModel.sketchController.collectAsStateWithLifecycle()
    val selectedLayerId by viewModel.selectedLayer.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .pointerInput(selectedTool) {
                if (selectedTool == DrawingToolLabel.ColorPicker) {
                    detectTapGestures { viewModel.selectTool(DrawingToolLabel.None) }
                }
            },
        topBar = { DrawingTopAppBar() },
    ) { paddingValues ->

        var colorPickerPosition by remember { mutableStateOf(Offset.Zero) }
        val curSketchController by remember(selectedLayerId) {
            mutableStateOf(sketchControllerMap[selectedLayerId] ?: SketchbookController())
        }

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
                    sketchController = curSketchController,
                    selectedTool = selectedTool,
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
                    layerList = layerList
                )
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                SketchScreen(
                    modifier = Modifier
                        .aspectRatio(ratioX / ratioY)
                        .align(Alignment.Center)
                    ,
                    layerList = layerList,
                    controllerMap = sketchControllerMap
                )
            }
        }

        if(selectedTool == DrawingToolLabel.ColorPicker) {
            ColorPicker(
                currentColor = curSketchController.currentPaintColor.value,
                offset = colorPickerPosition,
                setSelectedColor = {
                    curSketchController.setPaintColor(Color(it))
                }
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
private fun SideToolBar(
    modifier: Modifier,
    sketchController: SketchbookController,
    selectedTool: DrawingToolLabel,
    onSelectTool: (DrawingToolLabel) -> Unit,
    onGloballyPositioned: (LayoutCoordinates) -> Unit
) {

    Column(
        modifier
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BrushButton(
            isSelected = selectedTool == DrawingToolLabel.Brush,
            onClick = { onSelectTool(it) }
        )
        EraserButton(
            isSelected = selectedTool == DrawingToolLabel.Eraser,
            onClick = { onSelectTool(it) }
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
    controllerMap: HashMap<String, SketchbookController>
) {
    layerList.forEach { layerInfo ->
        Sketchbook(
            modifier = modifier
                .fillMaxSize()
            ,
            controller = controllerMap[layerInfo.id]!!,
        )
    }
}

@Composable
private fun LayerSection(
    modifier: Modifier,
    layerList: List<LayerInfo>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = layerList) { layerInfo ->
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(8.dp)
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
private fun UndoButton() {
    IgIconButton(
        onClick = { /*TODO*/ },
        icon = {
            Icon(
                imageVector = IgIcons.Undo,
                contentDescription = "Undo",
            )
        },
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
private fun BrushButton(
    isSelected: Boolean,
    onClick: (DrawingToolLabel) -> Unit,
) {
    IgIconButton(
        modifier = Modifier,
        onClick = { onClick(DrawingToolLabel.Brush) },
        icon = {
            Icon(
                imageVector = IgIcons.Brush,
                contentDescription = "brushButton",
                tint = toggleTine(isSelected)
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
                tint = toggleTine(isSelected)
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
private fun toggleTine(isSelected: Boolean) = if(isSelected) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSecondary