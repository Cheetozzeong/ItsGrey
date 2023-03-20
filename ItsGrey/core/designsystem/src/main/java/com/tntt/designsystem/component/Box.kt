package com.tntt.designsystem.component

import android.util.Log
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
import com.tntt.core.designsystem.theme.NiaTheme
import kotlin.math.roundToInt

enum class BoxState {
    None,
    Active,
    InActive
}

data class BoxData(
    val id: String,
    var state: BoxState = BoxState.None,
    var position: Offset = Offset(0f, 0f),
    var size: Size = Size(0f, 0f)
)

@Composable
fun Box(
    boxData: BoxData,
    innerContent: @Composable () -> Unit
) {
    val position = remember { mutableStateOf(boxData.position) }
    val size = remember { mutableStateOf(boxData.size) }

    Box(
        Modifier
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

@Composable
fun BoxForEdit(
    boxData: BoxData,
    onBoxStateChange: (id: String, state: BoxState) -> Unit,
    updateBoxData: (BoxData) -> Unit,
    onClickDelete: () -> Unit,
    innerContent: @Composable () -> Unit,
) {
    val isDialogShown = remember { mutableStateOf(boxData.state == BoxState.Active) }
    val position = remember { mutableStateOf(boxData.position) }
    val size = remember{ mutableStateOf(boxData.size) }
    val ratio = boxData.size.width / boxData.size.height

    val borderStyle = if (boxData.state == BoxState.Active) Stroke(width = 4f) else Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
    val borderColor = if (boxData.state == BoxState.Active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary

    if(boxData.state == BoxState.InActive) {
        isDialogShown.value = false
        updateBoxData(
            boxData.copy(
                state = BoxState.None,
                position = position.value,
                size = size.value
            )
        )
    }

    Box(
        Modifier
            .offset {
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
                                onBoxStateChange(boxData.id, BoxState.Active)
                                isDialogShown.value = true
                            }
                        )
                    }
                    BoxState.Active -> {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                position.value += dragAmount
                            },
                            onDragEnd = {
                                updateBoxData(
                                    boxData.copy(
                                        position = position.value
                                    )
                                )
                            }
                        )
                    }
                    else -> {}
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
                size.value,
                onDrag = { offset ->
                    size.value = Size(
                        size.value.width.plus(offset.x),
                        size.value.width.plus(offset.x) / ratio
                    )
                }
            )
            DrawDeleteCorner() {
                onClickDelete()
            }
        }
    }
}

private const val CORNER_SIZE = 30

@Composable
fun BoxDialog(
    dialogComponent: List<@Composable () -> Unit>,
    position: Offset,
) {

    val buttonHeight = ButtonDefaults.MinHeight.value
    val backgroundColor = MaterialTheme.colorScheme.surface

    Row(
        Modifier
            .offset{
                IntOffset(
                    position.x.roundToInt(),
                    (position.y - buttonHeight).roundToInt()
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
    onDrag: (Offset) -> Unit,
) {
    val cornerOffset = CORNER_SIZE / 2f

    val xOffset = size.width - cornerOffset
    val yOffset = size.height - cornerOffset

    Box(
        Modifier
            .size(CORNER_SIZE.dp)
            .offset(xOffset.dp, yOffset.dp)
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        onDrag(dragAmount)
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.OpenInFull,
            tint = MaterialTheme.colorScheme.onPrimary,
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
                size = Size(300f, 300f),
                position = Offset(40f, 40f)
            )
        )
    }


    Column(
        Modifier
            .fillMaxSize()
            .clickable {
                boxData = boxData.copy(state = BoxState.InActive)
            }
    ) {
        NiaTheme() {
            BoxForEdit(
                boxData,
                onBoxStateChange = { id, state ->
                    boxData = boxData.copy(state = state)
                },
                updateBoxData = { updateBoxData ->
                    boxData = updateBoxData
                },
                onClickDelete = {
                    Log.d("CORNER DELETE", "delete corner is clicked")
                },
                innerContent = {
                    TextField(value = "", onValueChange = {})
                },
            )
        }
    }
}
