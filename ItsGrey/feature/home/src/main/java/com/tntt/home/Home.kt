package com.tntt.home

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.theme.IgTheme
import com.tntt.designsystem.component.IgTabsMain
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.home.model.Book
import com.tntt.home.model.User
import com.tntt.model.*
import itsgrey.feature.home.R
import java.util.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.tntt.ui.PageForView

private enum class TabPage(val title: String) {
    Published("출판"),
    Working("작업중"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun Home(modifier: Modifier = Modifier) {

    val image = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.gunbam23)
    // dummy data
    val publishedBookList =
        listOf(
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image1",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc1",
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
                            id = "def1",
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
                            id = "ghi1",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "2",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image2",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc2",
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
                            id = "ghi2",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "3",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image3",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc3",
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
                            id = "def3",
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
                            id = "ghi3",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "4",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image4",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
                            id = "def4",
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
                            id = "ghi4",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image1",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc1",
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
                            id = "def1",
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
                            id = "ghi1",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "2",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image2",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc2",
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
                            id = "ghi2",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "3",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image3",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc3",
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
                            id = "def3",
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
                            id = "ghi3",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "4",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image4",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
                            id = "def4",
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
                            id = "ghi4",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image1",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc1",
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
                            id = "def1",
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
                            id = "ghi1",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "2",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image2",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc2",
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
                            id = "ghi2",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "3",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image3",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
                    listOf(
                        TextBoxInfo(
                            id = "abc3",
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
                            id = "def3",
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
                            id = "ghi3",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "4",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image4",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
                            id = "def4",
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
                            id = "ghi4",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "5",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image5",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
                            id = "def5",
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
                            id = "ghi5",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "6",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image6",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
                            id = "def6",
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
                            id = "ghi6",
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "1번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
            ),

        )

    val workingBookList =
        listOf(
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "2번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
            ),
            Book(
                bookInfo = BookInfo(
                    id = "2",
                    title = "345345번 제목",
                    saveDate = Date()
                ),
                Thumbnail(
                    ImageBoxInfo(
                        id = "image",
                        boxData = BoxData(
                            offsetRatioX = 0.0f,
                            offsetRatioY = 0.0f,
                            widthRatio = 1.0f,
                            heightRatio = 1.0f
                        )
                    ),
                    image = image,
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
            ),
        )

    // dummy data
    val user = User(name = "fakeUser",id = "id")

    var tabPage by remember { mutableStateOf(TabPage.Published) }

    val list = if (tabPage == TabPage.Published) publishedBookList else workingBookList

    IgTheme {
        val colorBackground = MaterialTheme.colorScheme.surface
        val borderColor = MaterialTheme.colorScheme.onPrimary

        Scaffold(
            modifier = Modifier,
            topBar = { IgTopAppBar(
                modifier = Modifier.padding(horizontal = 25.dp),
                titleRes = stringResource(R.string.home_toolbar_name),
                actions = {
                    Text(text = user.name)
                }) }
        ) { padding ->
            Column(
                Modifier
                    .padding(padding)
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorBackground)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            color = colorBackground,
                            topLeft = Offset(size.width, 0f),
                            size = Size(1.dp.toPx(), size.height),
                        )
                    }
                ) {
                    val titles = TabPage.values().map { it.title }
                    IgTabsMain(titles = titles, selectedTabIndex = { tabPage = TabPage.values()[it] })
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = MaterialTheme.colorScheme.surface)
                        .drawBehind {
                            val borderSize = 4.dp.toPx()
                            drawLine(
                                borderColor,
                                Offset(borderSize / 2, 0.dp.toPx() - 3.dp.toPx()),
                                Offset(borderSize / 2, size.height),
                                strokeWidth = borderSize,
                            )
                            drawLine(
                                borderColor,
                                Offset(size.width - borderSize / 2, size.height),
                                Offset(size.width - borderSize / 2, -3.dp.toPx()),
                                strokeWidth = borderSize
                            )
                        }
                ) {
                    BookList(modifier, list)
                }
            }
        }
    }
}

@Composable
private fun BookList(
    modifier: Modifier = Modifier,
    books: List<Book>
) {
    IgTheme {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxHeight()
            ) {
                items(books) { book ->
                    BookItem(modifier = Modifier, book = book)
                }
            }
    }
}

@Composable
private fun BookItem(
    modifier: Modifier,
    book: Book
){
    Column{
        Spacer(modifier = Modifier.height(20.dp))
        Row {
                Spacer(modifier = Modifier.width(20.dp))
                // TODO Page 이용해서 Thumbnail관련 코드들 받아오기
                Box(
                    modifier
                        .fillMaxSize()
                        .clickable(
                            /*TODO View로 이동*/
                            onClick = { Log.d("toViewr", "toViewr") }
                        )
                )
                {
                    Box(
                    ) {
                        PageForView(modifier = Modifier, thumbnail = book.thumbnail)
                    }
                    Box(
                    ) {
                        // BookInfo 출력
                        Text(text = book.bookInfo.title)
                    }

                }
        }
    }
}