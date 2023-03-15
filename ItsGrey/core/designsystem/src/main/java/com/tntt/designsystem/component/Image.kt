package com.tntt.designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tntt.core.designsystem.theme.NiaTheme
import kotlin.math.roundToInt

enum class BoxEvent {
    None,
    Active,
    Move,
    Resize,
    Dialog
}

data class BoxState(
    var event: BoxEvent = BoxEvent.None,
    var position: Offset = Offset(0f, 0f),
    var size: Size = Size(0f, 0f),
    val isDialogShown: Boolean = false
)

@Composable
fun ImageBox(
    initialBoxState: BoxState,
    onBoxStateChange: (BoxState) -> Unit,
    onClickDelete: () -> Unit
) {

    val position = remember { mutableStateOf(initialBoxState.position) }
    val size = remember { mutableStateOf(initialBoxState.size) }
    val event = remember { mutableStateOf(initialBoxState.event) }
    val ratio = initialBoxState.size.width / initialBoxState.size.height

    Box(
        Modifier
            .offset {
                IntOffset(
                    position.value.x.roundToInt(),
                    position.value.y.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (event.value != BoxEvent.None) return@detectTapGestures
                        event.value = BoxEvent.Active
                    },
                    onDoubleTap = {
                        if (event.value != BoxEvent.Active) return@detectTapGestures
                        event.value = BoxEvent.Dialog
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        if (event.value != BoxEvent.Active) return@detectDragGestures
                        event.value = BoxEvent.Move
                    },
                    onDragEnd = {
                        event.value = BoxEvent.Active
                    },
                    onDrag = { change, dragAmount ->
                        if (event.value == BoxEvent.Move) {
                            position.value += dragAmount
                        }
                    }
                )
            }
            .border(
                if (event.value != BoxEvent.None) 3.dp else 0.dp ,
                if (event.value != BoxEvent.None) Color.Red else Color.Gray,
            )
            .size(size.value.width.dp, size.value.height.dp)
    ) {
        if (event.value != BoxEvent.None) {
            DrawResizeCorner(size, event) { cornerOffset ->
                size.value = size.value.copy(
                    width = size.value.width.plus(cornerOffset.x),
                    height = size.value.width.plus(cornerOffset.x) / ratio
                )
            }
            DrawDeleteCorner() {
                onClickDelete()
            }
        }
//        if (boxState.isDialogShown) {
//            Dialog(
//                onDismissRequest = {
//                    boxState.isDialogShown = false
//                }
//            ) {
//                // 다이얼로그 컨텐츠
//            }
//        }
    }
}

private const val CORNER_SIZE = 30

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
            contentDescription = "Delete Icon",
        )
    }
}

@Composable
private fun DrawResizeCorner(
    size: MutableState<Size>,
    event: MutableState<BoxEvent>,
    onCornerHit: (Offset) -> Unit
) {
    val cornerOffset = CORNER_SIZE / 2f

    val xOffset = size.value.width - cornerOffset
    val yOffset = size.value.height - cornerOffset

    Box(
        Modifier
            .size(CORNER_SIZE.dp)
            .offset(xOffset.dp, yOffset.dp)
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        event.value = BoxEvent.Resize
                    },
                    onDrag = { _, dragAmount ->
                        onCornerHit(dragAmount)
                    },
                    onDragEnd = {
                        event.value = BoxEvent.Active
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.OpenInFull,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "Delete Icon",
            modifier = Modifier.rotate(90f)
        )
    }
}

@Preview(device = Devices.PIXEL_2, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewImage() {

    val boxState by remember {
        mutableStateOf(
            BoxState(
                size = Size(300f, 300f),
                position = Offset(20f, 30f)
            )
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        boxState.event = BoxEvent.None
                    }
                )
            }
    ) {
        NiaTheme() {
            ImageBox(
                boxState,
                onBoxStateChange = { boxState ->
                    boxState.size = boxState.size
                    boxState.position = boxState.position
                },
                onClickDelete = {
                    Log.d("CORNER DELETE", "delete corner is clicked")
                }
            )
        }
    }
}
