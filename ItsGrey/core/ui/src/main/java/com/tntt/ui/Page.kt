package com.tntt.ui

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tntt.model.BoxState
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail

@Composable
fun Page(
    thumbnail: Thumbnail,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f)
    ) {

    }
}

@Composable
@Preview
private fun PreviewPage() {

    Row(
        Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Page(thumbnail = Thumbnail(
                imageBox = ImageBoxInfo(
                    id = "id",
                    BoxState(
                        offsetRatioX = 0.1f,
                        offsetRatioY = 0.1f,
                        widthRatio = 0.5f,
                        heightRatio = 0.3f
                    )
                ),
                image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.happy),
                textBoxes = listOf(
                    TextBoxInfo(
                        id = "a",
                        text = "A",
                        fontSizeRatio = 0.1f,
                        BoxState(
                            offsetRatioX = 0.2f,
                            offsetRatioY = 0.2f,
                            widthRatio = 0.5f,
                            heightRatio = 0.3f
                        )
                    )
                )
            ))
        }

        Box(modifier = Modifier.weight(1f)) {
            Page(thumbnail = Thumbnail(
                imageBox = ImageBoxInfo(
                    id = "id",
                    BoxState(
                        offsetRatioX = 0.1f,
                        offsetRatioY = 0.1f,
                        widthRatio = 0.5f,
                        heightRatio = 0.3f
                    )
                ),
                image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.happy),
                textBoxes = listOf(
                    TextBoxInfo(
                        id = "a",
                        text = "A",
                        fontSizeRatio = 0.1f,
                        BoxState(
                            offsetRatioX = 0.2f,
                            offsetRatioY = 0.2f,
                            widthRatio = 0.5f,
                            heightRatio = 0.3f
                        )
                    )
                )
            ))
        }
    }
}