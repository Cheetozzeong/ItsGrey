package com.tntt.designsystem.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.core.designsystem.theme.NiaTheme
import itsgrey.core.designsystem.R

@Composable
fun ImageBox(
    boxData: BoxData,
    imageData: ImageBitmap
) {
    Box(boxData = boxData) {
        Image(
            // TODO: imageData 받는거로 변경
            painter = painterResource(id = R.drawable.icon_preview_button_48),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ImageBoxForEdit(
    boxData: BoxData,
    onBoxStateChange: (boxId: String, state: BoxState) -> Unit,
    updateBoxData: (BoxData) -> Unit,
    onClickDelete: () -> Unit,
    imageData: ImageBitmap
) {

    if(boxData.state == BoxState.Active) {
        BoxDialog(
            dialogComponent = listOf(
                { Button(onClick = { /*TODO*/ }) {
                    Text(text = "Zero")
                }},
                { Button(onClick = { /*TODO*/ }) {
                    Text(text = "First")
                }},
                { Button(onClick = { /*TODO*/ }) {
                    Text(text = "Second")
                }},
                { Button(onClick = { /*TODO*/ }) {
                    Text(text = "Last")
                }}
            ),
            position = boxData.position
        )
    }

    BoxForEdit(
        boxData = boxData,
        onBoxStateChange = { id, state ->
            onBoxStateChange(id, state)
        },
        updateBoxData = { newBoxData ->
            updateBoxData(newBoxData)
        },
        onClickDelete = {

        },
        innerContent = {
            Image(
                // TODO: imageData 받는거로 변경
                painter = painterResource(id = R.drawable.icon_preview_button_48),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
            )
        },
    )
}

@Preview
@Composable
private fun PreviewImage() {

    var boxData by remember {
        mutableStateOf(
            BoxData(
                id = "abc",
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
            ImageBox(
                boxData = boxData,
                imageData = ImageBitmap(10, 10, ImageBitmapConfig.Argb8888)
            )

            ImageBoxForEdit(
                boxData,
                onBoxStateChange = { id, state ->
                    Log.d("STATE_TEST - top", state.toString())
                    boxData = boxData.copy(state = state)
                    Log.d("STATE_TEST - change", boxData.state.toString())
                },
                updateBoxData = { updateBoxData ->
                    boxData = updateBoxData
                },
                onClickDelete = {
                    Log.d("CORNER DELETE", "delete corner is clicked")
                },
                imageData = ImageBitmap(300, 300, ImageBitmapConfig.Argb8888)
            )
        }
    }
}
