package com.tntt.designsystem.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

enum class BoxEvent {
    None,
    Active,
    Move,
    Resize,
    Dialog
}

data class BoxState(
    val event: BoxEvent = BoxEvent.None,
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
                if (event.value != BoxEvent.None) 1.dp else 0.dp,
                if (event.value != BoxEvent.None) Color.Red else Color.Gray
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

private const val CORNER_SIZE = 20

@Composable
private fun DrawResizeCorner(
    size: MutableState<Size>,
    event: MutableState<BoxEvent>,
    onCornerHit: (Offset) -> Unit
) {
    val cornerColor = Color(0xFFFFC107)
    val cornerOffset = CORNER_SIZE / 2f

    val xOffset = size.value.width - cornerOffset
    val yOffset = size.value.height - cornerOffset

    Box(
        Modifier
            .size(CORNER_SIZE.dp)
            .offset(xOffset.dp, yOffset.dp)
            .background(cornerColor, CircleShape)
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
            }
    )
}

@Composable
fun Image(
    bitmap: Bitmap,
    contentDescription: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview(device = Devices.PIXEL_2, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewImage() {

    val boxState by remember { mutableStateOf(BoxState(size = Size(300f, 300f))) }

    Column(
        Modifier.fillMaxSize()
    ) {
        ImageBox(
            boxState,
            onBoxStateChange = { boxState ->
                boxState.size = boxState.size
                boxState.position = boxState.position
            },
            onClickDelete = {

            }
        )
    }

}
