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
    parentSize: Size,
    textBoxInfo: TextBoxInfo,
) {

    val position = remember(parentSize) {
        mutableStateOf(
            Offset(
                textBoxInfo.boxData.offsetRatioX * parentSize.width,
                textBoxInfo.boxData.offsetRatioY * parentSize.height
            )
        )
    }
    val size = remember(parentSize) {
        mutableStateOf(
            Size(
                textBoxInfo.boxData.widthRatio * parentSize.width,
                textBoxInfo.boxData.heightRatio * parentSize.height
            )
        )
    }
    val fontSize = remember(parentSize, textBoxInfo) {
        mutableStateOf(
            textBoxInfo.fontSizeRatio * textBoxInfo.boxData.widthRatio * parentSize.width
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
    parentSize: Size,
    textBoxInfo: TextBoxInfo,
    updateTextBoxInfo: (TextBoxInfo) -> Unit,
    onClick: (id: String) -> Unit,
    onClickDelete: () -> Unit,
) {
    val state = remember { mutableStateOf(isSelected) }

    val position = remember(parentSize, textBoxInfo) {
        mutableStateOf(
            Offset(
                textBoxInfo.boxData.offsetRatioX * parentSize.width,
                textBoxInfo.boxData.offsetRatioY * parentSize.height
            )
        )
    }
    val size = remember(parentSize, textBoxInfo) {
        mutableStateOf(
            Size(
                textBoxInfo.boxData.widthRatio * parentSize.width,
                textBoxInfo.boxData.heightRatio * parentSize.height
            )
        )
    }
    val fontSize = remember(parentSize, textBoxInfo) {
        mutableStateOf(
            textBoxInfo.fontSizeRatio * textBoxInfo.boxData.widthRatio * parentSize.width
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
                    offsetRatioX = position.value.x / parentSize.width,
                    offsetRatioY = position.value.y / parentSize.height,
                    widthRatio = size.value.width / parentSize.width,
                    heightRatio = size.value.height / parentSize.height,
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
