package com.tntt.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tntt.core.designsystem.theme.IgTheme
import itsgrey.core.designsystem.R

@Composable
fun ImageBox(
    boxData: BoxData,
    imageData: ImageBitmap
) {
    Box(
        boxData = boxData,
        modifier = Modifier
    ) {
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
    updateBoxData: (BoxData) -> Unit,
    onClickDelete: () -> Unit,
    imageData: ImageBitmap,
    dialogComponent: List<@Composable () -> Unit>,
) {

    val isDialogShown = remember(boxData.state) { mutableStateOf(boxData.state == BoxState.Active) }

    Box() {
        if(isDialogShown.value) {
            BoxDialog(
                dialogComponent = dialogComponent,
                position = boxData.position
            )
        }

        BoxForEdit(
            boxData = boxData,
            updateBoxData = { newBoxData -> updateBoxData(newBoxData) },
            onClickDelete = { onClickDelete() },
            innerContent = {

                Image(
                    // TODO: imageData 받는거로 변경
                    painter = painterResource(id = R.drawable.icon_preview_button_48),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                )

            }
        )
    }
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
                boxData = boxData.copy(state = BoxState.None)
            }
    ) {
        IgTheme {
            ImageBox(
                boxData = boxData,
                imageData = ImageBitmap(10, 10, ImageBitmapConfig.Argb8888)
            )

            ImageBoxForEdit(
                boxData,
                updateBoxData = { updateBoxData -> boxData = updateBoxData },
                onClickDelete = {},
                imageData = ImageBitmap(300, 300, ImageBitmapConfig.Argb8888),
                dialogComponent = listOf {
                    Button(onClick = { /*TODO*/ }) {

                    }
                    Button(onClick = { /*TODO*/ }) {

                    }
                }
            )
        }
    }
}
