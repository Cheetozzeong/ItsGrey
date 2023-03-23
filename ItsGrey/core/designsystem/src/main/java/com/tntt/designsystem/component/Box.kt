package com.tntt.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.theme.IgTheme
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

enum class BoxState {
    None,
    Active
}

enum class ResizeType {
    Ratio,
    Free
}

enum class BoxEvent {
    Move,
    Resize,
}

data class BoxData(
    val id: String,
    var state: BoxState = BoxState.None,
    val resizeType: ResizeType = ResizeType.Free,
    var position: Offset = Offset(0f, 0f),
    var size: Size = Size(0f, 0f),
)

@Composable
fun Box(
    boxData: BoxData,
    modifier: Modifier,
    innerContent: @Composable () -> Unit
) {
    val position = remember { mutableStateOf(boxData.position) }
    val size = remember { mutableStateOf(boxData.size) }

    Box(
        modifier
            .offset {
                IntOffset(
                    position.value.x.roundToInt(),
                    position.value.y.roundToInt()
                )
            }
            .size(size.value.width.dp, size.value.height.dp)
    ) {
        innerContent()
    }
}

const val CORNER_SIZE = 30

@Composable
fun BoxForEdit(
    boxData: BoxData,
    updateBoxData: (BoxData) -> Unit,
    onClickDelete: () -> Unit,
    innerContent: @Composable () -> Unit
) {

    val density = remember { mutableStateOf(1f) }
    val position = remember { mutableStateOf(boxData.position) }
    val size = remember{ mutableStateOf(boxData.size) }
    val ratio by lazy { boxData.size.width / boxData.size.height }

    val borderStyle = if (boxData.state == BoxState.Active) Stroke(width = 4f) else Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 10f))
    val borderColor = if (boxData.state == BoxState.Active) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.tertiary

    Box(
        Modifier
            .offset {
                density.value = this.density
                IntOffset(
                    position.value.x.roundToInt(),
                    position.value.y.roundToInt()
                )
            }
            .pointerInput(boxData.state) {
                when (boxData.state) {
                    BoxState.None -> {
                        detectTapGestures(
                            onPress = {
                                updateBoxData(boxData.copy(state = BoxState.Active))
                            }
                        )
                    }
                    else -> {
                        val event = mutableStateOf(BoxEvent.Move)
                        detectDragGestures(
                            onDragStart = { offset ->
                                if (isCornerHit(offset, size.value, density.value)) {
                                    event.value = BoxEvent.Resize
                                }
                            },
                            onDrag = { change, dragAmount ->
                                when (event.value) {
                                    BoxEvent.Move -> {
                                        position.value += dragAmount
                                    }
                                    BoxEvent.Resize -> {
                                        when (boxData.resizeType) {
                                            ResizeType.Ratio -> {
                                                size.value = Size(
                                                    size.value.width + (dragAmount.x / density.value),
                                                    size.value.height + (dragAmount.x / density.value * ratio)
                                                )
                                            }
                                            ResizeType.Free -> {
                                                size.value = Size(
                                                    size.value.width + (dragAmount.x / density.value),
                                                    size.value.height + (dragAmount.y / density.value)
                                                )
                                            }
                                        }
                                    }
                                }
                            },
                            onDragEnd = {
                                when(event.value) {
                                    BoxEvent.Resize -> {
                                        updateBoxData(boxData.copy(size = size.value))
                                        event.value = BoxEvent.Move
                                    }
                                    BoxEvent.Move -> { updateBoxData(boxData.copy(position = position.value)) }
                                }
                            }
                        )
                    }
                }
            }
            .drawBehind {
                drawRect(
                    color = borderColor,
                    style = borderStyle
                )
            }
            .size(size.value.width.dp, size.value.height.dp)

    ) {
        innerContent()
        if (boxData.state == BoxState.Active) {
            DrawResizeCorner(
                size.value
            )
            DrawDeleteCorner() {
                onClickDelete()
            }
        }
    }
}

private fun isCornerHit(touchOff: Offset, boxSize: Size, density: Float): Boolean {

    val resizePointPosition = Offset(boxSize.width * density, boxSize.height * density) - Offset(x = CORNER_SIZE / 2 * density, y = CORNER_SIZE / 2 * density)
    val distance = sqrt((touchOff.x - resizePointPosition.x).pow(2) + (touchOff.y - resizePointPosition.y).pow(2))

    return distance <= CORNER_SIZE / 2 * density
}

@Composable
fun BoxDialog(
    dialogComponent: List<@Composable () -> Unit>,
    position: Offset,
) {
    val buttonHeight = ButtonDefaults.MinHeight.value
    val backgroundColor = MaterialTheme.colorScheme.surface

    Row(
        Modifier
            .offset {
                IntOffset(
                    position.x.roundToInt(),
                    (position.y - buttonHeight * 5f).roundToInt()
                )
            }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        dialogComponent.forEach {
            it.invoke()
        }
    }
}

@Composable
private fun DrawDeleteCorner(
    onClick: () -> Unit
) {
    val cornerOffset = CORNER_SIZE / 2f
    val xOffset = -cornerOffset
    val yOffset = -cornerOffset

    Box(
        modifier = Modifier
            .size(CORNER_SIZE.dp)
            .offset(xOffset.dp, yOffset.dp)
            .background(MaterialTheme.colorScheme.error, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Cancel,
            tint = MaterialTheme.colorScheme.onError,
            contentDescription = "Delete this box",
        )
    }
}

@Composable
private fun DrawResizeCorner(
    size: Size,
) {
    val xOffset = size.width - CORNER_SIZE
    val yOffset = size.height - CORNER_SIZE

    Box(
        Modifier
            .size(CORNER_SIZE.dp)
            .offset(xOffset.dp, yOffset.dp)
            .background(MaterialTheme.colorScheme.onSecondary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.OpenInFull,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = "박스 삭제 아이콘",
            modifier = Modifier.rotate(90f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.PIXEL_2, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewBox() {

    var boxData by remember(BoxState.None) {
        mutableStateOf(
            BoxData(
                id = "abc",
                size = Size(200f, 200f),
                position = Offset(40f, 40f),
                resizeType = ResizeType.Ratio
            )
        )
    }


    Column(
        Modifier
            .fillMaxSize()
            .clickable {
                boxData = boxData.copy(state = BoxState.None)
            }
    ) {
        IgTheme {
            BoxForEdit(
                boxData,
                updateBoxData = { updateBoxData ->
                    boxData = updateBoxData
                },
                onClickDelete = {
                },
                innerContent = {
                    TextField(value = "", onValueChange = {})
                }
            )
        }
    }
}
