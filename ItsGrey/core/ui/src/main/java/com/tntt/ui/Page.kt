package com.tntt.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
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
    var parentOffset by remember { mutableStateOf(Offset.Zero) }
    var parentSize by remember { mutableStateOf(Size.Zero) }
    val imageBoxInfo = remember(thumbnail) { thumbnail.imageBoxList }
    val contentBoxInfoList = remember(thumbnail) { thumbnail.textBoxList }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates ->
                parentOffset = layoutCoordinates.boundsInRoot().topLeft
                parentSize = layoutCoordinates.boundsInRoot().size
            }
    ){

        imageBoxInfo.forEach { imageBox ->
            ImageBox(
                parentOffset = parentOffset,
                parentSize = parentSize,
                imageBoxInfo = imageBox,
            )
        }
        contentBoxInfoList.forEach { textBoxInfo->
            with(textBoxInfo){
                TextBox(
                    parentSize = parentSize,
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
    imageBoxList: List<ImageBoxInfo>,
    selectedBoxId: String,
    updateTextBox: (TextBoxInfo) -> Unit,
    updateImageBox: (ImageBoxInfo) -> Unit,
    onBoxSelected: (String) -> Unit,
    deleteBox: (String) -> Unit,
    imageBoxDialogComponent: List<@Composable () -> Unit>
) {
    var parentSize by remember { mutableStateOf(Size.Zero) }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates ->
                parentSize = layoutCoordinates.boundsInRoot().size
            }
    ){
        imageBoxList.forEach { imageBoxInfo ->
            with(imageBoxInfo) {
                ImageBoxForEdit(
                    isSelected = id == selectedBoxId,
                    parentSize = parentSize,
                    imageBoxInfo = imageBoxInfo,
                    updateImageBoxInfo = { newImageBoxInfo -> updateImageBox(newImageBoxInfo) },
                    onClick = { id -> onBoxSelected(id) },
                    onClickDelete = { deleteBox(id) },
                    dialogComponent = imageBoxDialogComponent
                )
            }
        }

        textBoxList.forEach { textBoxInfo ->
            with(textBoxInfo) {
                TextBoxForEdit(
                    isSelected = id == selectedBoxId,
                    parentSize = parentSize,
                    textBoxInfo = this,
                    updateTextBoxInfo = { new -> updateTextBox(new) },
                    onClick = { id -> onBoxSelected(id) },
                    onClickDelete = { deleteBox(id) },
                )
            }
        }
    }
}
