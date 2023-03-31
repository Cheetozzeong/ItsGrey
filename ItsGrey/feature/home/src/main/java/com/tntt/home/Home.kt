package com.tntt.home

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgPlusPageButton
import com.tntt.designsystem.icon.IgIcons
import com.tntt.ui.PageForView
import java.text.SimpleDateFormat

private enum class TabPage(val title: String) {
    Published("출판"),
    Working("작업중"),
}

private enum class WindowSize(val item: Int) {
    COMPACT(2),
    MEDIUM(4),
    EXPANDED(5)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomePageRoute(
    modifier: Modifier = Modifier,
    onNewButtonClick: () -> Unit,
    onThumbnailClick: (String) -> Unit,
) {

    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val image = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.gunbam23)
    // dummy data

    val dateString = "2023-03-26T11:30:00+0900"
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val date = formatter.parse(dateString)

    // dummy data
    val publishedBookList =
        listOf(
            Book(
                bookInfo = BookInfo(
                    id = "1",
                    title = "이게 말도 안되게 길어지면 어뜨칼건데 대체....",
                    saveDate = date
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
                    id = "6",
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
                    id = "7",
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
                    id = "8",
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
                    id = "9",
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
                    id = "10",
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
                    id = "11",
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
                    id = "12",
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
                    id = "13",
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
        )

    // dummy data
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
            topBar = { IgTopAppBar (
                    modifier = Modifier
                        .padding(horizontal = 25.dp),
                    titleRes = stringResource(R.string.home_toolbar_name),
                    actions = {
                        Text(text = user.name)
                    }
                )
            }
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
                        .background(color = MaterialTheme.colorScheme.primary)
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
                    BookList(
                        modifier = modifier,
                        books = list,
                        tabPage = tabPage,
                        screenWidth = screenWidth,
                        onThumbnailClick = onThumbnailClick
                    )
                }
            }
        }
    }
}

@Composable
private fun BookList(
    modifier: Modifier = Modifier,
    books: List<Book>,
    tabPage: TabPage,
    screenWidth: Float,
    onThumbnailClick: (String) -> Unit
) {
    val windowSize = computeWindowSizeClasses(screenWidth)

    IgTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(windowSize.item),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 50.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxHeight()
        ) {
            items(books) { book ->
                BookItem(
                    modifier = Modifier,
                    book = book,
                    tabPage = tabPage,
                    onThumbnailClick = {
                        onThumbnailClick
                    }
                )
            }
            if (tabPage == TabPage.Working) {
                item {
                    IgPlusPageButton(onClick = {})
                }
            }
        }
    }
}



@Composable
private fun BookItem(
    modifier: Modifier,
    book: Book,
    onThumbnailClick: (String) -> Unit,
    tabPage: TabPage
) {
    val bookId = book.bookInfo.id
    Column {
            Row {
                Box(
                    modifier
                        .fillMaxSize()
                )
                {
                    Column {
                        Box (
                            Modifier
                                .border(width = 5.dp, MaterialTheme.colorScheme.onPrimary)
                                .clickable(
                                    onClick = { onThumbnailClick(bookId) } /*TODO 네비 연경*/
                                )
                        ) {
                            PageForView(modifier = Modifier, thumbnail = book.thumbnail)
                        }
                        Box {
                            Column {
                                Text(
                                    text = book.bookInfo.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                                if (tabPage == TabPage.Published) PublishedTimeAgoText(book.bookInfo.saveDate)
                                else if (tabPage == TabPage.Working) WorkingTimeAgoText(book.bookInfo.saveDate)
                            }
                        }
                    }
                }
            }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun PublishedTimeAgoText(date: Date) {
    val timestamp = date.time
    val formatter = SimpleDateFormat("출간일:yyyy.MM.dd", Locale.getDefault())
    val publishedDate = Date(timestamp)
    Text(formatter.format(publishedDate))
}

@Composable
fun WorkingTimeAgoText(date: Date) {
    val timestamp = date.time
    val now = Calendar.getInstance().timeInMillis
    val diff = now - timestamp

    val seconds = diff / 1000
    if (seconds < 60) {
        Text("${seconds}초 전")
        return
    }

    val minutes = seconds / 60
    if (minutes < 60) {
        Text("${minutes}분 전")
        return
    }

    val hours = minutes / 60
    if (hours < 24) {
        Text("${hours}시간 전")
        return
    }

    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val workingDate = Date(timestamp)
    Text(formatter.format(workingDate))
}

@Composable
private fun computeWindowSizeClasses(
    widthDp: Float,
): WindowSize {
    var currentWindowSize by remember { mutableStateOf(WindowSize.COMPACT) }

    currentWindowSize = when {
        widthDp < 600f -> WindowSize.COMPACT
        widthDp < 840f -> WindowSize.MEDIUM
        else ->
            WindowSize.EXPANDED
    }

    return currentWindowSize
}