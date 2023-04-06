package com.tntt.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.tntt.model.BoxData
import com.tntt.model.ImageBoxInfo

@Composable
fun ImageBox(
    parentOffset: Offset,
    parentSize: Size,
    imageBoxInfo: ImageBoxInfo,
) {
    Box(
        position = Offset(
            imageBoxInfo.boxData.offsetRatioX * parentSize.width,
            imageBoxInfo.boxData.offsetRatioY * parentSize.height
        ),
        size = Size(
            imageBoxInfo.boxData.widthRatio * parentSize.width,
            imageBoxInfo.boxData.heightRatio * parentSize.height
        )
    ) {
        Image(
            bitmap = imageBoxInfo.image.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageBoxForEdit(
    isSelected: Boolean,
    parentSize: Size,
    imageBoxInfo: ImageBoxInfo,
    updateImageBoxInfo: (ImageBoxInfo) -> Unit,
    onClick: (id: String) -> Unit,
    onClickDelete: () -> Unit,
    dialogComponent: List<@Composable () -> Unit>,
) {

    val state = remember { mutableStateOf(isSelected) }

    val position = remember(parentSize, imageBoxInfo) {
        mutableStateOf(
            Offset(
                imageBoxInfo.boxData.offsetRatioX * parentSize.width,
                imageBoxInfo.boxData.offsetRatioY * parentSize.height
            )
        )
    }
    var size by remember(parentSize, imageBoxInfo) {
        mutableStateOf(
            Size(
                imageBoxInfo.boxData.widthRatio * parentSize.width,
                imageBoxInfo.boxData.heightRatio * parentSize.height
            )
        )
    }

    if(state.value != isSelected) {
        state.value = isSelected
        updateImageBoxInfo(
            ImageBoxInfo(
                id = imageBoxInfo.id,
                boxData = BoxData(
                    offsetRatioX = position.value.x / parentSize.width,
                    offsetRatioY = position.value.y / parentSize.height,
                    widthRatio = size.width / parentSize.width,
                    heightRatio = size.height / parentSize.height,
                ),
                image = imageBoxInfo.image
            )
        )
    }

    if(isSelected) {
        BoxDialog(
            dialogComponent = dialogComponent,
            position = position.value
        )
    }

    BoxForEdit(
        isSelected = isSelected,
        inputPosition = position.value,
        inputSize = size,
        resizeType = ResizeType.Ratio,
        updatePosition = { newPosition -> position.value = newPosition },
        updateSize = { newSize -> size = newSize },
        onClickDelete = { onClickDelete() },
        innerContent = {
            Image(
                bitmap = imageBoxInfo.image.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(state.value, imageBoxInfo) {
                        if (!isSelected) {
                            detectTapGestures {
                                onClick(imageBoxInfo.id)
                            }
                        }
                    },
                contentScale = ContentScale.Fit
            )
        }
    )
}
