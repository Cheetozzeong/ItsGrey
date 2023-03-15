package com.tntt.designsystem.component

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
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
    var event: BoxEvent = BoxEvent.None,
    var position: Offset = Offset(0f, 0f),
    var size: Size = Size.Zero,
    var isDialogShown: Boolean = false
)

@Composable
fun ImageBox(
    initialBoxState: BoxState,
) {
    val event = remember { mutableStateOf(initialBoxState.event) }
    val offsetX = remember { mutableStateOf(initialBoxState.position.x) }
    val offsetY = remember { mutableStateOf(initialBoxState.position.y) }
    val width = remember { mutableStateOf(initialBoxState.size.width) }
    val height = remember { mutableStateOf(initialBoxState.size.height) }
    val ratio = initialBoxState.size.width / initialBoxState.size.height

    Box(
        Modifier
            .offset {
                IntOffset(
                    offsetX.value.roundToInt(),
                    offsetY.value.roundToInt(),
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (event.value != BoxEvent.None) return@detectTapGestures
                        event.value = BoxEvent.Active
                        Log.d("TEST", event.value.toString())

                    },
                    onDoubleTap = {
                        if (event.value != BoxEvent.Active) return@detectTapGestures
                        event.value = BoxEvent.Dialog
                        Log.d("TEST", event.value.toString())
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        if (event.value != BoxEvent.Active) return@detectDragGestures
                        event.value = BoxEvent.Move
                        Log.d("TEST", event.value.toString())
                    },
                    onDragEnd = {
                        event.value = BoxEvent.Active
                        Log.d("TEST", event.value.toString())
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        if (event.value == BoxEvent.Move) {
                            offsetX.value += dragAmount.x
                            offsetY.value += dragAmount.y
                        } else if (event.value == BoxEvent.Resize) {
                            width.value += dragAmount.x
                            height.value = width.value / ratio
                        }
                        Log.d("TEST", event.value.toString())
                        Log.d("TEST", "${offsetX.value} ${offsetY.value}")
                        Log.d("TEST", "${width.value} ${height.value}")
                    }
                )
            }
            .border(
                if (event.value != BoxEvent.None) 1.dp else 0.dp,
                if (event.value != BoxEvent.None) Color.Red else Color.Gray
            )
            .size(width.value.dp, height.value.dp)
    ) {
        if (event.value != BoxEvent.None) {
            DrawCorners(width.value, height.value, event)
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

@Composable
private fun DrawCorners(
    width: Float,
    height: Float,
    event: MutableState<BoxEvent>
) {
    val cornerSize = 20 // 꼭지점의 크기
    val cornerColor = Color(0xFFFFC107)
    val cornerRadius = 100
    val cornerOffset = cornerSize / 2f
    val cornerShapes = listOf(
        RoundedCornerShape(cornerRadius),
        RoundedCornerShape(cornerRadius),
        RoundedCornerShape(cornerRadius),
        RoundedCornerShape(cornerRadius),
    )
    cornerShapes.forEachIndexed { i, shape ->
        val xOffset = if (i == 1 || i == 3) width - cornerOffset else 0f - cornerOffset
        val yOffset = if (i == 2 || i == 3) height - cornerOffset else 0f - cornerOffset
        Box(
            Modifier
                .size(cornerSize.dp)
                .offset(xOffset.dp, yOffset.dp)
                .background(cornerColor, shape)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            if(event.value == BoxEvent.Active) event.value = BoxEvent.Resize
                        }
                    )
                }
        )
    }
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
        ImageBox(boxState)
    }

}
