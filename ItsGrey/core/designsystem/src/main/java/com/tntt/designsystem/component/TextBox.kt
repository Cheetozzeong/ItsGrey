package com.tntt.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

data class TextBoxData(
    val text: String,
    val fontSize: Float,
)

@Composable
fun TextBox(
    boxData: BoxData,
    textData: TextBoxData
) {
    Box(boxData = boxData) {
        Text(
            text = textData.text,
            fontSize = textData.fontSize.sp
        )
    }
}

@Preview
@Composable
private fun PreviewTextBox() {
    val boxData by remember {
        mutableStateOf(
            BoxData(
                id = "abc",
                size = Size(300f, Float.NaN),
                position = Offset(0f, 0f)
            )
        )
    }

    TextBox(boxData = boxData, textData = TextBoxData("abcdefg", 25f))
}