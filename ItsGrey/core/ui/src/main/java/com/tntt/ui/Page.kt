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
    var parent by rememberSaveable(stateSaver = RectSaver){ mutableStateOf(Rect(Offset.Zero, Size.Zero)) }
    val imageBoxInfo = remember(thumbnail) { thumbnail.imageBoxList }
    val contentBoxInfoList = remember(thumbnail) { thumbnail.textBoxList }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates ->
                parent = layoutCoordinates.boundsInRoot()
            }
    ){

        imageBoxInfo.forEach { imageBox ->
            ImageBox(
                parent = parent,
                imageBoxInfo = imageBox,
            )
        }
        contentBoxInfoList.forEach { textBoxInfo->
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
    imageBoxList: List<ImageBoxInfo>,
    selectedBoxId: String,
    updateTextBox: (TextBoxInfo) -> Unit,
    updateImageBox: (ImageBoxInfo) -> Unit,
    onBoxSelected: (String) -> Unit,
    deleteBox: (String) -> Unit,
    imageBoxDialogComponent: List<@Composable () -> Unit>
) {
    var parent by rememberSaveable(stateSaver = RectSaver) { mutableStateOf(Rect(Offset.Zero, Size.Zero)) }

    Box(
        modifier
            .aspectRatio(2f / 3f)
            .onGloballyPositioned { layoutCoordinates -> parent = layoutCoordinates.boundsInRoot() }
    ){
        imageBoxList.forEach { imageBoxInfo ->
            with(imageBoxInfo) {
                ImageBoxForEdit(
                    isSelected = id == selectedBoxId,
                    parent = parent,
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
