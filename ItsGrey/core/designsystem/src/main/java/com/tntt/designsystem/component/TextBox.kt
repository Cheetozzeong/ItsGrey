package com.tntt.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.tntt.designsystem.theme.IgTheme

data class TextBoxData(
    val text: String,
    val fontSize: Float,
)

@Composable
fun TextBox(
    boxData: BoxData,
    textData: TextBoxData,
    modifier: Modifier
) {
    Box(
        boxData = boxData,
        modifier = modifier
    ) {
        Text(
            text = textData.text,
            fontSize = textData.fontSize.sp
        )
    }
}

@Composable
fun TextBoxForEdit(
    boxData: BoxData,
    updateBoxData: (BoxData) -> Unit,
    onTextBoxDataChange: (TextBoxData) -> Unit,
    onClickDelete: () -> Unit,
    textData: TextBoxData
) {

    val isEnabled by remember(boxData) { mutableStateOf(boxData.state == BoxState.Active) }

    BoxForEdit(
        boxData = boxData,
        updateBoxData = { newBoxData ->  updateBoxData(newBoxData) },
        onClickDelete = { onClickDelete() },
        innerContent = {

            BasicTextField(
                value = textData.text,
                onValueChange = {
                    onTextBoxDataChange(textData.copy(text = it))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding((CORNER_SIZE / 2f).dp)
                    .pointerInput(isEnabled) {
                        detectTapGestures {
                            if (!isEnabled) {
                                updateBoxData(boxData.copy(state = BoxState.Active))
                            }
                            isEnabled != isEnabled
                        }
                    },
                enabled = isEnabled,
            )

        }
    )
}

@Preview
@Composable
private fun PreviewTextBox() {
    val boxData = remember {
        mutableStateOf(
            BoxData(
                id = "abc",
                size = Size(300f, 200f),
                position = Offset(0f, 0f)
            )
        )
    }
    var textData by remember {
        mutableStateOf(
            TextBoxData("abcdefg", 25f)
        )
    }

    IgTheme {
        Column(
            Modifier
                .fillMaxSize()
                .clickable {
                    boxData.value = boxData.value.copy(state = BoxState.None)
                }
        ) {
            //    TextBox(boxData = boxData, textData = TextBoxData("abcdefg", 25f))

            TextBoxForEdit(
                boxData = boxData.value,
                textData = textData,
                updateBoxData = { newBoxData -> boxData.value = newBoxData },
                onTextBoxDataChange = { textData = it },
                onClickDelete = {}
            )
        }
    }
}