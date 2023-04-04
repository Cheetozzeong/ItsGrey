package com.tntt.designsystem.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import com.tntt.model.BoxData
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
                .padding(horizontal = (CORNER_SIZE / 2f).dp)
        )
    }
}

@Composable
fun TextBoxForEdit(
    isSelected: Boolean,
    parent: Rect,
    textBoxInfo: TextBoxInfo,
    updateTextBoxInfo: (TextBoxInfo) -> Unit,
    onClick: (id: String) -> Unit,
    onClickDelete: () -> Unit,
) {
    val state = remember { mutableStateOf(isSelected) }

    val position = remember(parent, textBoxInfo) {
        mutableStateOf(
            Offset(
                textBoxInfo.boxData.offsetRatioX * parent.width,
                textBoxInfo.boxData.offsetRatioY * parent.height
            )
        )
    }
    val size = remember(parent, textBoxInfo) {
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
    val text = remember(textBoxInfo) {
        mutableStateOf(
            textBoxInfo.text
        )
    }

    if(state.value != isSelected) {
        state.value = isSelected
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
                )
            )
        )
    }

    BoxForEdit(
        isSelected = isSelected,
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
                    .padding(horizontal = (CORNER_SIZE / 2f).dp)
                    .pointerInput(state.value, textBoxInfo) {
                        detectTapGestures {
                            if (!isSelected) {
                                onClick(textBoxInfo.id)
                            }
                        }
                    },
                enabled = isSelected,
                textStyle = TextStyle(
                    fontSize = fontSize.value.sp
                )
            )
        }
    )
}
