package com.tntt.ui

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import com.tntt.designsystem.component.*
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
        thumbnail.textBoxList.toMutableStateList()
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
    textBoxList: List<TextBoxInfo>,
    imageBox: List<ImageBoxInfo>,
    image: ImageBitmap,
    selectedBoxId: String,
    onImageToDrawClick: (String) -> Unit,
    updateTextBox: (TextBoxInfo) -> Unit,
    updateImageBox: (ImageBoxInfo) -> Unit,
    onBoxSelected: (String) -> Unit,
    deleteBox: (String) -> Unit,
) {
    var parent by rememberSaveable(stateSaver = RectSaver) { mutableStateOf(Rect(Offset.Zero, Size.Zero)) }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .fillMaxHeight()
            .onGloballyPositioned { layoutCoordinates ->
                parent = layoutCoordinates.boundsInRoot()
            }
    ){
        imageBox.forEach { imageBoxInfo ->
            with(imageBoxInfo) {
                ImageBoxForEdit(
                    isSelected = id == selectedBoxId,
                    parent = parent,
                    imageBoxInfo = imageBoxInfo,
                    imageBitmap = image,
                    updateImageBoxInfo = { newImageBoxInfo -> updateImageBox(newImageBoxInfo) },
                    onClick = { id -> onBoxSelected(id) },
                    onClickDelete = { deleteBox(id) },
                    dialogComponent = listOf {
                        IgTextButton(
                            onClick = { onImageToDrawClick(id) },
                            text = {Text(text = "이미지 변경")}
                        )
                    }
                )
            }
        }

        textBoxList.forEach { textBoxInfo ->
            with(textBoxInfo) {
                TextBoxForEdit(
                    isSelected = id == selectedBoxId,
                    parent = parent,
                    textBoxInfo = this,
                    updateTextBoxInfo = { new -> updateTextBox(new) },
                    onClick = { id -> onBoxSelected(id) },
                    onClickDelete = { deleteBox(id) },
                )
            }
        }
    }
}
