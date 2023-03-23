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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.tntt.core.designsystem.theme.IgTheme

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBoxForEdit(
    boxData: BoxData,
    onBoxStateChange: (id: String, state: BoxState) -> Unit,
    onTextBoxDataChange: (TextBoxData) -> Unit,
    textData: TextBoxData
) {

    val isEnabled by remember(boxData) { mutableStateOf(boxData.state == BoxState.Active) }

    BoxForEdit(
        boxData = boxData,
        onBoxStateChange = { id, state -> onBoxStateChange(id, state)},
        updateBoxData = {},
        onClickDelete = { /*TODO*/ },
        innerContent = {
            TextField(
                value = textData.text,
                onValueChange = {
                    onTextBoxDataChange(textData.copy(text = it))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(isEnabled) {
                        detectTapGestures {
                            if (!isEnabled) {
                                onBoxStateChange(boxData.id, BoxState.Active)
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
    var boxData by remember {
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
                    boxData = boxData.copy(state = BoxState.None)
                }
        ) {
            //    TextBox(boxData = boxData, textData = TextBoxData("abcdefg", 25f))

            TextBoxForEdit(
                boxData = boxData,
                onBoxStateChange = {id, state ->
                    boxData = boxData.copy(state = state)
                },
                onTextBoxDataChange = { textData = it },
                textData = textData
            )
        }
    }
}