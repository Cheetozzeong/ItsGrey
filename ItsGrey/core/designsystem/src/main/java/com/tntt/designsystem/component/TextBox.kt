package com.tntt.designsystem.component

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import com.tntt.designsystem.theme.IgTheme
import com.tntt.model.BoxData
import com.tntt.model.BoxState
import com.tntt.model.TextBoxInfo

data class TextBoxData(
    val text: String,
    val fontSize: Float,
)

@Composable
fun TextBox(
    parent: Rect,
    textBoxInfo: TextBoxInfo,
) {

    val position = remember(parent) {
        mutableStateOf(
            Offset(
                textBoxInfo.boxData.offsetRatioX * parent.width,
                textBoxInfo.boxData.offsetRatioY * parent.height
            )
        )
    }
    val size = remember(parent) {
        mutableStateOf(
            Size(
                textBoxInfo.boxData.widthRatio * parent.width,
                textBoxInfo.boxData.heightRatio * parent.height
            )
        )
    }
    val fontSize = remember(parent, textBoxInfo) {
        mutableStateOf(
            textBoxInfo.fontSizeRatio * textBoxInfo.boxData.widthRatio * parent.width
        )
    }

    Box(
        position = position.value,
        size = size.value,
    ) {
        Text(
            text = textBoxInfo.text,
            fontSize = fontSize.value.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding((CORNER_SIZE / 2f).dp)
        )
    }
}

@Composable
fun TextBoxForEdit(
    parent: Rect,
    textBoxInfo: TextBoxInfo,
    updateTextBoxInfo: (TextBoxInfo) -> Unit,
    onClickDelete: () -> Unit,
) {

    val isEnabled by remember(textBoxInfo) { mutableStateOf(textBoxInfo.boxData.state == BoxState.Active) }
    val position = remember(parent) {
        mutableStateOf(
            Offset(
                textBoxInfo.boxData.offsetRatioX * parent.width,
                textBoxInfo.boxData.offsetRatioY * parent.height
            )
        )
    }
    val size = remember(parent) {
        mutableStateOf(
            Size(
                textBoxInfo.boxData.widthRatio * parent.width,
                textBoxInfo.boxData.heightRatio * parent.height
            )
        )
    }
    val fontSize = remember(parent, textBoxInfo) {
        mutableStateOf(
            textBoxInfo.fontSizeRatio * textBoxInfo.boxData.widthRatio * parent.width
        )
    }
    val text = remember {
        mutableStateOf(
            textBoxInfo.text
        )
    }

    if(textBoxInfo.boxData.state == BoxState.InActive) {
        updateTextBoxInfo(
            TextBoxInfo(
                id = textBoxInfo.id,
                text = text.value,
                fontSizeRatio = fontSize.value / size.value.width,
                boxData = BoxData(
                    offsetRatioX = position.value.x / parent.width,
                    offsetRatioY = position.value.y / parent.height,
                    widthRatio = size.value.width / parent.width,
                    heightRatio = size.value.height / parent.height,
                    state = BoxState.None
                )
            )
        )
    }

    BoxForEdit(
        boxState = textBoxInfo.boxData.state,
        inputPosition = position.value,
        inputSize = size.value,
        resizeType = ResizeType.Free,
        updatePosition = { newPosition ->
            position.value = newPosition
        },
        updateSize = { newSize ->
            size.value = newSize
        },
        onClickDelete = { onClickDelete() },
        innerContent = {

            BasicTextField(
                value = text.value,
                onValueChange = { newText ->
                    text.value = newText
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding((CORNER_SIZE / 2f).dp)
                    .pointerInput(isEnabled) {
                        detectTapGestures {
                            if (!isEnabled) {
                                updateTextBoxInfo(
                                    textBoxInfo.copy(
                                        boxData = textBoxInfo.boxData.copy(
                                            state = BoxState.Active
                                        )
                                    )
                                )
                            }
                            isEnabled != isEnabled
                        }
                    },
                enabled = isEnabled,
                textStyle = TextStyle(
                    fontSize = fontSize.value.sp
                )
            )
        }
    )
}

@Preview
@Composable
private fun PreviewTextBox() {

    val textBoxInfoL = remember() {
        mutableStateOf(
            TextBoxInfo(
                id = "abc",
                text = "LEFT",
                fontSizeRatio = 0.05f,
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.1f,
                    widthRatio = 0.5f,
                    heightRatio = 0.3f
                )
            )
        )
    }
    val textBoxInfoR = remember() {
        mutableStateOf(
            TextBoxInfo(
                id = "abc",
                text = "RIGHT",
                fontSizeRatio = 0.05f,
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.1f,
                    widthRatio = 0.5f,
                    heightRatio = 0.3f
                )
            )
        )
    }

    var parentL by rememberSaveable(stateSaver = RectSaver) {
        mutableStateOf(
            Rect(Offset.Zero, Size.Zero)
        )
    }
    var parentR by rememberSaveable(stateSaver = RectSaver) {
        mutableStateOf(
            Rect(Offset.Zero, Size.Zero)
        )
    }

    IgTheme {
        Row(
            Modifier
                .width(600.dp)
                .height(400.dp)
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .aspectRatio(2f / 3f)
                    .onGloballyPositioned { layoutCoordinates ->
                        parentL = layoutCoordinates.boundsInRoot()
                        Log.d("TEST - left", "${layoutCoordinates.boundsInRoot()}")
                    }
                    .clickable {
                        textBoxInfoL.value = textBoxInfoL.value.copy(
                            boxData = textBoxInfoL.value.boxData.copy(
                                state = BoxState.InActive
                            )
                        )
                    }
            ) {
                TextBoxForEdit(
                    parent = parentL,
                    textBoxInfo = textBoxInfoL.value,
                    updateTextBoxInfo = { newTextBoxInfo ->
                        textBoxInfoL.value = newTextBoxInfo
                    },
                    onClickDelete = {}
                )
            }
            Box(
                Modifier
                    .weight(1f)
                    .aspectRatio(2f / 3f)
                    .onGloballyPositioned { layoutCoordinates ->
                        parentR = layoutCoordinates.boundsInRoot()
                        Log.d("TEST - right", "${layoutCoordinates.boundsInRoot()}")
                    }
                    .clickable {
                        textBoxInfoR.value = textBoxInfoR.value.copy(
                            boxData = textBoxInfoR.value.boxData.copy(
                                state = BoxState.InActive
                            )
                        )
                    }
            ) {
                TextBoxForEdit(
                    parent = parentL,
                    textBoxInfo = textBoxInfoR.value,
                    updateTextBoxInfo = { newTextBoxInfo ->
                        textBoxInfoR.value = newTextBoxInfo
                    },
                    onClickDelete = {}
                )
            }

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
