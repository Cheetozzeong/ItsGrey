package com.tntt.editbook

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tntt.designsystem.theme.IgTheme
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun EditBookTopAppBar() {
    IgTopAppBar(
        title = "BOOK TITLE",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = { /*TODO*/ },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.PreviewTwo,
                    contentDescription = "Preview",
                    tint = Color.Black,
                )
            }
        }
    )
}

@Preview
@Composable
private fun SideSection(
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val listState = rememberLazyListState()
    val selectedPage = remember { mutableStateOf<Int?>(null) }

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            itemsIndexed(ThumbnailOfPageData) { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    ThumbnailOfPage(
                        drawable = item,
                        modifier = Modifier
                            .clickable { selectedPage.value = index }
                            .border(
                                width = 4.dp,
                                color = if (selectedPage.value == index) MaterialTheme.colorScheme.outline else Color.Transparent
                            )
                            .fillParentMaxWidth(0.3f)
                            .fillParentMaxHeight(0.8f)
                    )
                    Text(
                        text = "${index + 1}",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .wrapContentSize(Alignment.BottomCenter),
                        fontSize = 8.sp,
                    )
                }
            }
        }
    }
    else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxHeight()
        ) {
            itemsIndexed(ThumbnailOfPageData) {index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    ThumbnailOfPage(
                        drawable = item,
                        modifier = Modifier
                            .height(150.dp)
                            .clickable { selectedPage.value = index }
                            .border(
                                width = 4.dp,
                                color = if (selectedPage.value == index) MaterialTheme.colorScheme.outline else Color.Transparent
                            )
                    )
                    Text(
                        text = "${index + 1}",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun ThumbnailOfPage(
@DrawableRes drawable: Int,
modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            Image(
                /*
                TODO: 추후에 resource 삭제 요망
                * */
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private val ThumbnailOfPageData = listOf(
    R.drawable.ab1_inversions,
    R.drawable.ab2_quick_yoga,
    R.drawable.ab3_stretching,
    R.drawable.ab4_tabata,
    R.drawable.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga,
    R.drawable.fc1_short_mantras,
    R.drawable.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage,
    R.drawable.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down
)

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
private fun MainSection(
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState()
    HorizontalPager(
        state = pagerState,
        count = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ThumbnailOfPageData.size
        } else{
            ThumbnailOfPageData.size / 2
        },
        modifier = Modifier
        .fillMaxSize()
        .let {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                it.padding(9.dp, 16.dp)
            } else {
                it.padding(9.dp, 50.dp)
            }
        },
    ) { page ->
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ThumbnailOfPage(
                drawable = ThumbnailOfPageData[page],
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .padding(50.dp)
            )
        } else {
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding()
            ) {
                ThumbnailOfPage(
                    drawable = ThumbnailOfPageData[page*2],
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(1f)
                        .padding(9.dp)
                )
                if (page < ThumbnailOfPageData.size - 1) {
                    ThumbnailOfPage(
                        drawable = ThumbnailOfPageData[(page*2)+1],
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(1f)
                            .padding(9.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "vertical")
@Composable
private fun VerticalModeScreen(modifier: Modifier = Modifier) {
    IgTheme {
        Scaffold(
            topBar = { EditBookTopAppBar() }
        ) { padding ->
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.75f)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    MainSection()
                }
                Box(modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.tertiary)
                ) {
                    SideSection()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "horizontal", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun HorizontalModeScreen(modifier: Modifier = Modifier) {
    IgTheme {
        Scaffold(
            topBar = { EditBookTopAppBar() }
        ) { padding ->
            Row(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            color = Color.LightGray,
                            topLeft = Offset(size.width - 1.dp.toPx(), 0f),
                            size = Size(1.dp.toPx(), size.height),
                        )
                    }
                ) {
                    SideSection()
                }
                Box(
                    modifier = Modifier
                        .weight(0.75f)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    MainSection()
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditBookScreen(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        VerticalModeScreen()
    } else {
        HorizontalModeScreen()
    }
}