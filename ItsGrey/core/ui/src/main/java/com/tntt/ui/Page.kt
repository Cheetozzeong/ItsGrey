//package com.tntt.ui
//
//import android.content.res.Resources
//import android.graphics.BitmapFactory
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.tooling.preview.Preview
//import com.tntt.designsystem.component.BoxData
//import com.tntt.designsystem.component.ImageBox
//import com.tntt.designsystem.component.ResizeType
//import com.tntt.model.*
//
//@Composable
//fun PageForView(
//    thumbnail: Thumbnail,
//) {
//
//    val imageBoxInfo = remember { mutableListOf(thumbnail.imageBox) }
//
//    Box(
//        Modifier
//            .fillMaxWidth()
//            .aspectRatio(2f / 3f)
//            .onGloballyPositioned { layoutCoordinates ->
//                imageBoxInfo.
//                layoutCoordinates.size.height
//                layoutCoordinates.size.width
//            }
//    ) {
//        BoxData(
//            id = thumbnail.imageBox.id,
//            resizeType = ResizeType.Ratio,
//            position = Offset(imageBoxInfo.size)
//        )
//        ImageBox(
//            boxData = thumbnail.imageBox.boxState,
//            imageData = thumbnail.image.asImageBitmap()
//        )
//    }
//}
//
//@Composable
//@Preview
//private fun PreviewPage() {
//
//    Row(
//        Modifier.fillMaxSize()
//    ) {
//        Box(modifier = Modifier.weight(1f)) {
//            PageForView(thumbnail = Thumbnail(
//                imageBox = ImageBoxInfo(
//                    id = "id",
//                    BoxState(
//                        offsetRatioX = 0.1f,
//                        offsetRatioY = 0.1f,
//                        widthRatio = 0.5f,
//                        heightRatio = 0.3f
//                    )
//                ),
//                image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.happy),
//                textBoxes = listOf(
//                    TextBoxInfo(
//                        id = "a",
//                        text = "A",
//                        fontSizeRatio = 0.1f,
//                        BoxState(
//                            offsetRatioX = 0.2f,
//                            offsetRatioY = 0.2f,
//                            widthRatio = 0.5f,
//                            heightRatio = 0.3f
//                        )
//                    )
//                )
//            ))
//        }
//
//        Box(modifier = Modifier.weight(1f)) {
//            PageForView(thumbnail = Thumbnail(
//                imageBox = ImageBoxInfo(
//                    id = "id",
//                    BoxData(
//                        offsetRatioX = 0.1f,
//                        offsetRatioY = 0.1f,
//                        widthRatio = 0.5f,
//                        heightRatio = 0.3f
//                    )
//                ),
//                image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.happy),
//                textBoxes = listOf(
//                    TextBoxInfo(
//                        id = "a",
//                        text = "A",
//                        fontSizeRatio = 0.1f,
//                        BoxData(
//                            offsetRatioX = 0.2f,
//                            offsetRatioY = 0.2f,
//                            widthRatio = 0.5f,
//                            heightRatio = 0.3f
//                        )
//                    )
//                )
//            ))
//        }
//    }
//}