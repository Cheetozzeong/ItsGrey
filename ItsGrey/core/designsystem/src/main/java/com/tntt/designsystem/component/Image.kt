package com.tntt.designsystem.component

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun Box(
    initialBoxState: BoxState,
    modifier: Modifier,
    innerContent: @Composable () -> Unit
) {
    val position = remember { mutableStateOf(initialBoxState.position) }
    val size = remember { mutableStateOf(initialBoxState.size) }

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

@Composable
fun BoxForEdit(
    initialBoxState: BoxState,
    onClickDelete: () -> Unit,
    innerContent: @Composable () -> Unit,
    dialog: @Composable () -> Unit
) {

    val position = remember { mutableStateOf(initialBoxState.position) }
    val size = remember { mutableStateOf(initialBoxState.size) }
    val event = remember { mutableStateOf(initialBoxState.event) }
    val isDialogShown = remember { mutableStateOf(initialBoxState.isDialogShown) }
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
                        if (event.value == BoxEvent.Active) {
                            isDialogShown.value = !isDialogShown.value
                        }
                        if (event.value == BoxEvent.Active) return@detectTapGestures
                        event.value = BoxEvent.Active
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        Log.d("TEST", "$it")
                        if (event.value != BoxEvent.Active) return@detectDragGestures
                        event.value = BoxEvent.Move
                    },
                    onDragEnd = {
                        event.value = BoxEvent.Active
                    },
                    onDrag = { _, dragAmount ->
                        if (event.value == BoxEvent.Move) {
                            position.value += dragAmount
                        }
                    }
                )
            }
            .border(
                if (event.value != BoxEvent.None) 3.dp else 0.dp,
                if (event.value != BoxEvent.None) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
            )
            .size(size.value.width.dp, size.value.height.dp)

    ) {
        innerContent()
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
        if (isDialogShown.value) {
            dialog()
        }
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
            contentDescription = "Delete this box",
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
            contentDescription = "박스 삭제 아이콘",
            modifier = Modifier.rotate(90f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.PIXEL_2, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewImage() {

    val boxState by remember {
        mutableStateOf(
            BoxState(
                size = Size(300f, 300f),
                position = Offset(40f, 40f)
            )
        )
    }
    var content by remember {
        mutableStateOf("")
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
            BoxForEdit(
                boxState,
                onClickDelete = {
                    Log.d("CORNER DELETE", "delete corner is clicked")
                },
                innerContent = {
                    Image(
                        painter = BitmapPainter(ImageBitmap(100, 100, ImageBitmapConfig.Argb8888)),
                        contentDescription = "for test",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
//                    TextField(
//                        value = content,
//                        onValueChange = { changed ->
//                            content = changed
//                        }
//                    )
                },
                dialog = {
                    Row (
                        modifier = Modifier.offset(
                            0.dp, (-50).dp
                        ).background(MaterialTheme.colorScheme.surface)
                    ) {
                        Box() {
                            Text(text = "TEST Dialog")
                        }
                    }
                }
            )
        }
    }
}
