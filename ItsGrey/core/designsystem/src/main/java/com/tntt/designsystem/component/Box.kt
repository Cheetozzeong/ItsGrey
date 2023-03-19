package com.tntt.designsystem.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
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
import com.tntt.core.designsystem.theme.NiaTheme
import kotlin.math.roundToInt

enum class BoxState {
    None,
    Active,
    InActive
}

data class BoxData(
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
    onBoxStateChange: (boxId: Int, state: BoxState) -> Unit,
    updateBoxData: (BoxData) -> Unit,
    onClickDelete: () -> Unit,
    innerContent: @Composable () -> Unit,
) {

    val id = 1
    val isDialogShown = false

    val state = boxData.state
    val position = remember { mutableStateOf(boxData.position) }
    val size = remember{ mutableStateOf(boxData.size) }
    val ratio = boxData.size.width / boxData.size.height

    if(state == BoxState.InActive) {
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
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (state == BoxState.None) {
                            onBoxStateChange(id, BoxState.Active)
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        if (state != BoxState.Active) return@detectDragGestures
                    },
                    onDrag = { _, dragAmount ->
                        position.value += dragAmount
                    }
                )
            }
            .border(
                if (state == BoxState.None) 0.dp else 3.dp,
                if (state == BoxState.None) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
            )
            .size(size.value.width.dp, size.value.height.dp)

    ) {
        innerContent()
        if (state == BoxState.Active) {
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
                }
            )
        }
    }
}
