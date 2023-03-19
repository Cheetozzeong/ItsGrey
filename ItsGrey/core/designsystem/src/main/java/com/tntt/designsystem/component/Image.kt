package com.tntt.designsystem.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
    onBoxStateChange: (boxId: Int, state: BoxState) -> Unit,
    updateBoxData: (BoxData) -> Unit,
    onClickDelete: () -> Unit,
    imageData: ImageBitmap
) {
    BoxForEdit(
        boxData = boxData,
        onBoxStateChange = { id, state ->
            onBoxStateChange(id, state)
        },
        updateBoxData = { newBoxData ->
            updateBoxData(newBoxData)
        },
        onClickDelete = {

        }
    ) {
        Image(
            // TODO: imageData 받는거로 변경
            painter = painterResource(id = R.drawable.icon_preview_button_48),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    // TODO: Dialog 추가
                }
        )
    }
}

@Preview
@Composable
private fun PreviewImage() {

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
            ImageBox(
                boxData = boxData,
                imageData = ImageBitmap(10, 10, ImageBitmapConfig.Argb8888)
            )

            ImageBoxForEdit(
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
                imageData = ImageBitmap(10, 10, ImageBitmapConfig.Argb8888)
            )
        }
    }
}
