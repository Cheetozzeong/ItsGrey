package com.tntt.designsystem.component

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
            .size(
                (size.width / density.value).dp,
                (size.height / density.value).dp
            )
    ) {
        innerContent()
    }
}

@Composable
fun BoxForEdit(
    isSelected: Boolean,
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
    val size = remember(inputSize) { mutableStateOf(inputSize) }
    val ratio by lazy { inputSize.width / inputSize.height }

    val borderStyle = if (isSelected) Stroke(width = 4f) else Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 10f))
    val borderColor = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.tertiary

    Box(
        Modifier
            .offset {
                density.value = this.density
                IntOffset(
                    position.value.x.roundToInt(),
                    position.value.y.roundToInt()
                )
            }
            .pointerInput(inputPosition, isSelected) {
                if(isSelected) {
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
            .size(
                (size.value.width / density.value).dp,
                (size.value.height / density.value).dp
            )
    ) {
        innerContent()
    }

    DrawResizeCorner(
        key = inputSize,
        isSelected,
        position.value.times(1 / density.value),
        size.value.times(1 / density.value),
        onDrag = { dragAmount ->
            when (resizeType) {
                ResizeType.Ratio -> {
                    size.value = Size(
                        size.value.width + dragAmount.x,
                        size.value.height + dragAmount.x * ratio
                    )
                }
                ResizeType.Free -> {
                    size.value = Size(
                        size.value.width + dragAmount.x,
                        size.value.height + dragAmount.y
                    )
                }
            }
        },
        onDragEnd = {
            updateSize(size.value)
        }
    )

    DrawDeleteCorner(
        position = position.value.times(1 / density.value),
        isSelected = isSelected
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
    isSelected: Boolean,
    position: Offset,
    onClick: () -> Unit
) {
    if(isSelected) {

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
    isSelected: Boolean,
    position: Offset,
    size: Size,
    onDrag: (dragAmount: Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    if(isSelected) {

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


val RectSaver = Saver<Rect, Bundle>(
    save = { rect ->
        Bundle().apply {
            putFloat("left", rect.left)
            putFloat("top", rect.top)
            putFloat("right", rect.right)
            putFloat("bottom", rect.bottom)
        }
    },
    restore = { bundle ->
        Rect(
            bundle.getFloat("left"),
            bundle.getFloat("top"),
            bundle.getFloat("right"),
            bundle.getFloat("bottom")
        )
    }
)
