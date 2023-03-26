package com.tntt.designsystem.component

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.theme.IgTheme
import com.tntt.model.BoxData
import com.tntt.model.BoxState
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import itsgrey.core.designsystem.R

@Composable
fun ImageBox(
    parent: Rect,
    imageBoxInfo: ImageBoxInfo,
    imageBitmap: ImageBitmap
) {
    Box(
        position = Offset(
            imageBoxInfo.boxData.offsetRatioX * parent.width,
            imageBoxInfo.boxData.offsetRatioY * parent.height
        ),
        size = Size(
            imageBoxInfo.boxData.widthRatio * parent.width,
            imageBoxInfo.boxData.heightRatio * parent.height
        )
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun ImageBoxForEdit(
    parent: Rect,
    imageBoxInfo: ImageBoxInfo,
    imageBitmap: ImageBitmap,
    updateImageBoxInfo: (ImageBoxInfo) -> Unit,
    onClickDelete: () -> Unit,
    dialogComponent: List<@Composable () -> Unit>,
) {

    val isDialogShown = remember(imageBoxInfo.boxData.state) { mutableStateOf(imageBoxInfo.boxData.state == BoxState.Active) }
    val position = remember(parent) {
        mutableStateOf(
            Offset(
                imageBoxInfo.boxData.offsetRatioX * parent.width,
                imageBoxInfo.boxData.offsetRatioY * parent.height
            )
        )
    }
    val size = remember(parent) {
        mutableStateOf(
            Size(
                imageBoxInfo.boxData.widthRatio * parent.width,
                imageBoxInfo.boxData.heightRatio * parent.height
            )
        )
    }

    if(imageBoxInfo.boxData.state == BoxState.InActive) {
        updateImageBoxInfo(
            ImageBoxInfo(
                id = imageBoxInfo.id,
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


    Box() {
        if(isDialogShown.value) {
            BoxDialog(
                dialogComponent = dialogComponent,
                position = position.value
            )
        }

        BoxForEdit(
            boxState = imageBoxInfo.boxData.state,
            inputPosition = position.value,
            inputSize = size.value,
            resizeType = ResizeType.Ratio,
            updatePosition = { newPosition ->
                position.value = newPosition
            },
            updateSize = { newSize ->
                size.value = newSize
            },
            onClickDelete = { onClickDelete() },
            innerContent = {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(imageBoxInfo.boxData.state) {
                            if(imageBoxInfo.boxData.state == BoxState.None) {
                                detectTapGestures {
                                    updateImageBoxInfo(
                                        imageBoxInfo.copy(
                                            boxData = imageBoxInfo.boxData.copy(
                                                state = BoxState.Active
                                            )
                                        )
                                    )
                                }
                            }
                        },
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewImage() {

    val imageBoxInfo = remember() {
        mutableStateOf(
            ImageBoxInfo(
                id = "abc",
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.1f,
                    widthRatio = 0.5f,
                    heightRatio = 0.3f
                )
            )
        )
    }

    val bitmap = ImageBitmap.imageResource(R.drawable.happy)

    var parentL by rememberSaveable(stateSaver = RectSaver) {
        mutableStateOf(
            Rect(Offset.Zero, Size.Zero)
        )
    }

    Column(
        Modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates ->
                parentL = layoutCoordinates.boundsInRoot()
                Log.d("TEST - left", "${layoutCoordinates.boundsInRoot()}")
            }
            .clickable {
                imageBoxInfo.value = imageBoxInfo.value.copy(
                    boxData = imageBoxInfo.value.boxData.copy(
                        state = BoxState.InActive
                    )
                )
            }
    ) {
        IgTheme {
//            ImageBox(
//                imageBoxInfo = imageBoxInfo.value,
//                imageBitmap = bitmap,
//                parent = parentL
//            )

            ImageBoxForEdit(
                parent = parentL,
                imageBoxInfo = imageBoxInfo.value,
                imageBitmap = bitmap,
                updateImageBoxInfo = { newImageBoxInfo -> imageBoxInfo.value = newImageBoxInfo },
                onClickDelete = {},
                dialogComponent = listOf {
                    IgTextButton(onClick = { /*TODO*/ }, text = { Text(text = "abc") })
                    IgTextButton(onClick = { /*TODO*/ }, text = { Text(text = "abc") })
                }
            )
        }
    }
}
