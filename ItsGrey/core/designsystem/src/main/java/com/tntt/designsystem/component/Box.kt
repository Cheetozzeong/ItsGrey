package com.tntt.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tntt.designsystem.theme.IgTheme
import com.tntt.model.BoxData
import com.tntt.model.BoxState
import com.tntt.model.TextBoxInfo
import kotlin.math.roundToInt

enum class ResizeType {
    Ratio,
    Free
}

const val CORNER_SIZE = 30

@Composable
fun Box(
    position: Offset,
    size: Size,
    innerContent: @Composable () -> Unit
) {

    val density = rememberSaveable { mutableStateOf(1f) }

    Box(
        Modifier
            .offset {
                density.value = this.density
                IntOffset(
                    position.x.roundToInt(),
                    position.y.roundToInt()
                )
            }
            .size((size.width / density.value).dp, (size.height / density.value).dp)
    ) {
        innerContent()
    }
}

@Composable
fun BoxForEdit(
    boxState: BoxState,
    inputPosition: Offset,
    inputSize: Size,
    updatePosition: (Offset) -> Unit,
    updateSize: (Size) -> Unit,
    resizeType: ResizeType,
    onClickDelete: () -> Unit,
    innerContent: @Composable () -> Unit,
) {

    val density = rememberSaveable { mutableStateOf(1f) }
    val position = remember(inputPosition) { mutableStateOf(inputPosition) }
    val size = remember(inputSize) { mutableStateOf(inputSize.times(1 / density.value)) }
    val ratio by lazy { inputSize.width / inputSize.height }

    val borderStyle = if (boxState == BoxState.Active) Stroke(width = 4f) else Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 10f))
    val borderColor = if (boxState == BoxState.Active) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.tertiary

    Box(
        Modifier
            .offset {
                density.value = this.density
                IntOffset(
                    position.value.x.roundToInt(),
                    position.value.y.roundToInt()
                )
            }
            .pointerInput(inputPosition, boxState) {
                if(boxState == BoxState.Active) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            position.value += dragAmount
                        },
                        onDragEnd = {
                            updatePosition(position.value)
                        }
                    )
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
    }

    DrawResizeCorner(
        key = inputSize,
        boxState,
        position.value.times(1 / density.value),
        size.value,
        onDrag = { dragAmount ->
            when (resizeType) {
                ResizeType.Ratio -> {
                    size.value = Size(
                        size.value.width + dragAmount.x / density.value,
                        size.value.height + dragAmount.x * ratio / density.value
                    )
                }
                ResizeType.Free -> {
                    size.value = Size(
                        size.value.width + dragAmount.x / density.value,
                        size.value.height + dragAmount.y / density.value
                    )
                }
            }
        },
        onDragEnd = {
            updateSize(size.value.times(density.value))
        }
    )

    DrawDeleteCorner(
        position = position.value.times(1 / density.value),
        boxState = boxState
    ) {
        onClickDelete()
    }
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
    boxState: BoxState,
    position: Offset,
    onClick: () -> Unit
) {
    if(boxState == BoxState.Active) {

        val cornerOffset = CORNER_SIZE / 2f
        val xOffset = position.x - cornerOffset
        val yOffset = position.y - cornerOffset

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
}

@Composable
private fun DrawResizeCorner(
    key: Size,
    boxState: BoxState,
    position: Offset,
    size: Size,
    onDrag: (dragAmount: Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    if(boxState == BoxState.Active) {

        val cornerOffset = CORNER_SIZE / 2f
        val xOffset by remember(position, size) {
            mutableStateOf(position.x + size.width - cornerOffset)
        }
        val yOffset by remember(position, size) {
            mutableStateOf(position.y + size.height - cornerOffset)
        }

        Box(
            Modifier
                .size(CORNER_SIZE.dp)
                .offset(xOffset.dp, yOffset.dp)
                .background(MaterialTheme.colorScheme.onSecondary, CircleShape)
                .pointerInput(key) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            onDrag(dragAmount)
                        },
                        onDragEnd = {
                            onDragEnd()
                        }

                    )
                }
            ,
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
}

@Preview(device = Devices.PIXEL_2, widthDp = 200, heightDp = 300)
@Composable
private fun PreviewBox() {

    val textBoxInfo = remember() {
        mutableStateOf(
            TextBoxInfo(
                id = "abc",
                text = "ABCD",
                fontSizeRatio = 0.1f,
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.1f,
                    widthRatio = 0.8f,
                    heightRatio = 0.3f
                )
            )
        )
    }

    val pageSize by remember {
        mutableStateOf(
            Size(200f, 300f)
        )
    }


    Column(
        Modifier
            .fillMaxSize()
            .clickable {
            }
    ) {
        IgTheme {
            Box(
                position = Offset(
                    textBoxInfo.value.boxData.offsetRatioX * pageSize.width,
                    textBoxInfo.value.boxData.offsetRatioY * pageSize.height
                ),
                size = Size(
                    textBoxInfo.value.boxData.widthRatio * pageSize.width,
                    textBoxInfo.value.boxData.heightRatio * pageSize.height
                ),
                innerContent = {
                    Text(
                        text = textBoxInfo.value.text,
                        fontSize = (textBoxInfo.value.boxData.widthRatio * pageSize.width * textBoxInfo.value.fontSizeRatio).sp
                    )
                }
            )

            BoxForEdit(
                boxState = BoxState.None,
                inputPosition = Offset(
                    textBoxInfo.value.boxData.offsetRatioX * pageSize.width,
                    textBoxInfo.value.boxData.offsetRatioY * pageSize.height
                ),
                inputSize = Size(
                    textBoxInfo.value.boxData.widthRatio * pageSize.width,
                    textBoxInfo.value.boxData.heightRatio * pageSize.height
                ),
                updatePosition = { newPosition ->
                    textBoxInfo.value = textBoxInfo.value.copy(
                        boxData = textBoxInfo.value.boxData.copy(
                            offsetRatioX = newPosition.x / pageSize.width,
                            offsetRatioY = newPosition.y / pageSize.height
                        )
                    )
                },
                updateSize = { newSize ->
                    textBoxInfo.value = textBoxInfo.value.copy(
                        boxData = textBoxInfo.value.boxData.copy(
                            widthRatio = newSize.width / pageSize.width,
                            heightRatio = newSize.height / pageSize.height
                        )
                    )
                },
                resizeType = ResizeType.Free,
                onClickDelete = { /*TODO*/ },
                innerContent = {
                    BasicTextField(
                        value = textBoxInfo.value.text,
                        onValueChange = { newText ->
                            textBoxInfo.value = textBoxInfo.value.copy(
                                text = newText
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = (textBoxInfo.value.boxData.widthRatio * pageSize.width * textBoxInfo.value.fontSizeRatio).sp
                        )
                    )
                }
            )
        }
    }
}
