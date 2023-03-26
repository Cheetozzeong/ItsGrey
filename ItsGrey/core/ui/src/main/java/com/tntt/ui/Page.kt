package com.tntt.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.tntt.designsystem.component.*
import com.tntt.designsystem.theme.IgTheme
import com.tntt.model.*

@Composable
fun PageForView(
    modifier: Modifier,
    thumbnail: Thumbnail,
) {
    var parent by rememberSaveable(stateSaver = RectSaver){
        mutableStateOf(
            Rect(Offset.Zero, Size.Zero)
        )
    }
    val imageBoxInfo = remember{ mutableStateOf(thumbnail.imageBox) }
    val contentBoxInfoList = remember{
        thumbnail.textBoxes.toMutableStateList()
    }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates ->
                parent = layoutCoordinates.boundsInRoot()
            }
    ){
        ImageBox(parent = parent, imageBoxInfo = imageBoxInfo.value, imageBitmap = thumbnail.image.asImageBitmap())
        contentBoxInfoList.map{textBoxInfo->
            with(textBoxInfo){
                TextBox(
                    parent = parent,
                    textBoxInfo = TextBoxInfo(
                        id,
                        text,
                        fontSizeRatio,
                        boxData
                    )
                )
            }
        }
    }
}

@Composable
fun PageForEdit(
    modifier: Modifier,
    thumbnail: Thumbnail
) {
    val activeBoxId = remember {
        mutableStateOf("")
    }
    var parent by rememberSaveable(stateSaver = RectSaver) {
        mutableStateOf(
            Rect(Offset.Zero, Size.Zero)
        )
    }
    val imageBoxInfo = remember { mutableStateOf(thumbnail.imageBox) }
    val contentBoxInfoList = remember {
        thumbnail.textBoxes.toMutableStateList()
    }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates ->
                parent = layoutCoordinates.boundsInRoot()
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    activeBoxId.value = ""
                }
            }
    ){
        ImageBoxForEdit(
            activeBoxId = activeBoxId.value,
            parent = parent,
            imageBoxInfo = imageBoxInfo.value,
            imageBitmap = thumbnail.image.asImageBitmap(),
            updateImageBoxInfo = { newImageBoxInfo ->
                imageBoxInfo.value = newImageBoxInfo
            },
            onClickDelete = { /*TODO*/ },
            onClick = { id -> activeBoxId.value = id },
            dialogComponent = listOf()
        )
        contentBoxInfoList.mapIndexed { index, textBoxInfo->
            with(textBoxInfo){
                TextBoxForEdit(
                    activeBoxId = activeBoxId.value,
                    parent = parent,
                    textBoxInfo = this,
                    updateTextBoxInfo = { new ->
                        contentBoxInfoList[index] = new
                    },
                    onClick = { id -> activeBoxId.value = id },
                    onClickDelete = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewPage() {

    IgTheme() {
        Row (
            Modifier.fillMaxSize()
        ){
            PageForView(
                modifier = Modifier.weight(1f),
                thumbnail = Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.2f,
                            offsetRatioY = 0.2f,
                            widthRatio = 0.5f,
                            heightRatio = 0.3f
                        )
                    ),
                    image = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888),
                    listOf(
                        TextBoxInfo(
                            id = "abc",
                            text = "ABC",
                            fontSizeRatio = 0.05f,
                            boxData = BoxData(
                                offsetRatioX = 0.2f,
                                offsetRatioY = 0.2f,
                                widthRatio = 0.5f,
                                heightRatio = 0.3f
                            )
                        ),
                        TextBoxInfo(
                            id = "def",
                            text = "DEF",
                            fontSizeRatio = 0.05f,
                            boxData = BoxData(
                                offsetRatioX = 0.2f,
                                offsetRatioY = 0.4f,
                                widthRatio = 0.5f,
                                heightRatio = 0.3f
                            )
                        ),
                        TextBoxInfo(
                            id = "ghi",
                            text = "GHI",
                            fontSizeRatio = 0.05f,
                            boxData = BoxData(
                                offsetRatioX = 0.2f,
                                offsetRatioY = 0.6f,
                                widthRatio = 0.5f,
                                heightRatio = 0.3f
                            )
                        )
                    )
                )
            )
            PageForEdit(
                modifier = Modifier.weight(1f),
                thumbnail = Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.2f,
                            offsetRatioY = 0.1f,
                            widthRatio = 0.8f,
                            heightRatio = 0.5f
                        )
                    ),
                    image = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.img),
                    listOf(
                        TextBoxInfo(
                            id = "abc",
                            text = "ABC",
                            fontSizeRatio = 0.05f,
                            boxData = BoxData(
                                offsetRatioX = 0.2f,
                                offsetRatioY = 0.5f,
                                widthRatio = 0.5f,
                                heightRatio = 0.3f
                            )
                        ),
                        TextBoxInfo(
                            id = "def",
                            text = "DEF",
                            fontSizeRatio = 0.05f,
                            boxData = BoxData(
                                offsetRatioX = 0.2f,
                                offsetRatioY = 0.7f,
                                widthRatio = 0.5f,
                                heightRatio = 0.3f
                            )
                        ),
                        TextBoxInfo(
                            id = "ghi",
                            text = "GHI",
                            fontSizeRatio = 0.05f,
                            boxData = BoxData(
                                offsetRatioX = 0.1f,
                                offsetRatioY = 0.8f,
                                widthRatio = 0.6f,
                                heightRatio = 0.3f
                            )
                        )
                    )
                )
            )
        }
    }

}