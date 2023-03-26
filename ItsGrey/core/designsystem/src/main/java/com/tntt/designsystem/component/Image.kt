package com.tntt.designsystem.component

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
            modifier = Modifier.fillMaxSize()
        )
    }
}

//@Composable
//fun ImageBoxForEdit(
//    boxData: BoxData,
//    updateBoxData: (BoxData) -> Unit,
//    onClickDelete: () -> Unit,
//    imageData: ImageBitmap,
//    dialogComponent: List<@Composable () -> Unit>,
//) {
//
//    val isDialogShown = remember(boxData.state) { mutableStateOf(boxData.state == BoxState.Active) }
//
//    Box() {
//        if(isDialogShown.value) {
//            BoxDialog(
//                dialogComponent = dialogComponent,
//                position = boxData.position
//            )
//        }
//
//        BoxForEdit(
//            boxData = boxData,
//            updateBoxData = { newBoxData -> updateBoxData(newBoxData) },
//            onClickDelete = { onClickDelete() },
//            innerContent = {
//
//                Image(
//                    // TODO: imageData 받는거로 변경
//                    painter = painterResource(id = R.drawable.icon_preview_button_48),
//                    contentDescription = "",
//                    modifier = Modifier
//                        .fillMaxSize(),
//                )
//
//            }
//        )
//    }
//}

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
            ImageBox(
                imageBoxInfo = imageBoxInfo.value,
                imageBitmap = bitmap,
                parent = parentL
            )

//            ImageBoxForEdit(
//                boxData,
//                updateBoxData = { updateBoxData -> boxData = updateBoxData },
//                onClickDelete = {},
//                imageData = ImageBitmap(300, 300, ImageBitmapConfig.Argb8888),
//                dialogComponent = listOf {
//                    Button(onClick = { /*TODO*/ }) {
//
//                    }
//                    Button(onClick = { /*TODO*/ }) {
//
//                    }
//                }
//            )
        }
    }
}
