package com.tntt.editbook

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tntt.designsystem.theme.IgTheme
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.editbook.model.Page
import com.tntt.model.*
import com.tntt.ui.PageForView
import org.burnoutcrew.reorderable.*


@Composable
internal fun EditBookPageRoute(
    viewModel: EditBookViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit
) {
    val bookTitle by viewModel.bookTitle.collectAsStateWithLifecycle()
    val thumbnailOfPageDataList by viewModel.thumbnailOfPageData.collectAsStateWithLifecycle()
    val selectedPage by viewModel.selectedPage.collectAsStateWithLifecycle()

    EditBookScreen(
        bookTitle = bookTitle,
        thumbnailOfPageData = thumbnailOfPageDataList,
        movePage = viewModel::movePage,
        selectedPage = selectedPage,
        updateSelectedPage = viewModel::updateSelectedPage
    )
}

@Composable
private fun EditBookScreen(
    bookTitle: String,
    thumbnailOfPageData: List<Page>,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        VerticalModeScreen(bookTitle, thumbnailOfPageData, movePage, selectedPage, updateSelectedPage)
    } else {
        HorizontalModeScreen(bookTitle, thumbnailOfPageData, movePage, selectedPage, updateSelectedPage)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditBookTopAppBar(
    bookTitle: String
) {
    val configuration = LocalConfiguration.current

    IgTopAppBar(
        title = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (bookTitle.length >= 12) bookTitle.substring(0, 12) + "..." else bookTitle
        } else {
            if (bookTitle.length >= 48) bookTitle.substring(0, 48) + "..." else bookTitle
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerticalModeScreen(
    bookTitle: String,
    thumbnailOfPageData: List<Page>,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit
) {

    IgTheme {
        Scaffold(
            topBar = { EditBookTopAppBar(bookTitle) }
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
                    MainSection(thumbnailOfPageData, selectedPage, updateSelectedPage)
                }
                Box(modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.tertiary)
                ) {
                    VerticalSideSection(thumbnailOfPageData, movePage, selectedPage, updateSelectedPage)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalModeScreen(
    bookTitle: String,
    thumbnailOfPageData: List<Page>,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit
) {
    val recColor = MaterialTheme.colorScheme.tertiary

    IgTheme {
        Scaffold(
            topBar = { EditBookTopAppBar(bookTitle) }
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
                            color = recColor,
                            topLeft = Offset(size.width - 1.dp.toPx(), 0f),
                            size = Size(1.dp.toPx(), size.height),
                        )
                    }
                ) {
                    HorizontalSideSection(thumbnailOfPageData, movePage, selectedPage, updateSelectedPage)
                }
                Box(
                    modifier = Modifier
                        .weight(0.75f)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    MainSection(thumbnailOfPageData, selectedPage, updateSelectedPage)
                }
            }
        }
    }
}

@Composable
private fun VerticalSideSection(
    thumbnailOfPageData: List<Page>,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
) {
    val lazyListState = rememberReorderableLazyListState(onMove = { from, to ->
        movePage(from.index, to.index)
    })

    LazyRow(
        state = lazyListState.listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .then(Modifier.reorderable(lazyListState))
            .detectReorderAfterLongPress(lazyListState)
    ) {
        items(thumbnailOfPageData, { Page -> Page.pageInfo.order }) { item ->
            ReorderableItem(lazyListState, item.pageInfo.order ) { isDragging ->
                val scale = animateFloatAsState(if (isDragging) 1.1f else 1.0f)
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .scale(scale.value)
                        .shadow(elevation.value, RoundedCornerShape(4.dp))
                ) {
                    PageForView(
                        thumbnail = item.thumbnail,
                        modifier = Modifier
                            .clickable {
                                updateSelectedPage(item.pageInfo.order)
                            }
                            .border(
                                width = 4.dp,
                                color = if (selectedPage == item.pageInfo.order) MaterialTheme.colorScheme.outline else Color.Transparent
                            )
                            .fillParentMaxWidth(0.3f)
                            .fillParentMaxHeight(0.8f)
                    )
                    Text(
                        text = "${item.pageInfo.order + 1}",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .wrapContentSize(Alignment.BottomCenter),
                        fontSize = 8.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalSideSection(
    thumbnailOfPageData: List<Page>,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyGridState = rememberReorderableLazyGridState(onMove = { from, to ->
        movePage(from.index, to.index)
    })

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState.gridState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxHeight()
            .reorderable(lazyGridState)
            .detectReorderAfterLongPress(lazyGridState)
    ) {
        items(thumbnailOfPageData, { Page -> Page.pageInfo.order }) { item ->
            ReorderableItem(lazyGridState, item.pageInfo.order) { isDragging ->
                val scale = animateFloatAsState(if (isDragging) 1.1f else 1.0f)
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                        .scale(scale.value)
                        .shadow(elevation.value, RoundedCornerShape(4.dp))
                ) {
                    PageForView(
                        thumbnail = item.thumbnail,
                        modifier = Modifier
                            .height(150.dp)
                            .clickable {
                                updateSelectedPage(item.pageInfo.order)
                            }
                            .border(
                                width = 4.dp,
                                color = if (selectedPage == item.pageInfo.order) MaterialTheme.colorScheme.outline else Color.Transparent
                            )
                    )
                    Text(
                        text = "${item.pageInfo.order + 1}",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainSection(
    thumbnailOfPageData: List<Page>,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState(initialPage = selectedPage)

    LaunchedEffect(pagerState.currentPage) {
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            updateSelectedPage(pagerState.currentPage)
        } else {
            if (selectedPage in pagerState.currentPage * 2..pagerState.currentPage * 2 + 1) return@LaunchedEffect
            updateSelectedPage(pagerState.currentPage * 2)
        }
    }

    LaunchedEffect(selectedPage) {
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            pagerState.scrollToPage(selectedPage)
        } else {
            pagerState.scrollToPage(selectedPage / 2)
        }
    }

    HorizontalPager(
        state = pagerState,
        count = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            thumbnailOfPageData.size
        } else{
            thumbnailOfPageData.size / 2
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
            PageForView(
                thumbnail = thumbnailOfPageData[page].thumbnail,
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
                PageForView(
                    thumbnail = thumbnailOfPageData[page*2].thumbnail,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(1f)
                        .padding(9.dp)
                )
                if (page < thumbnailOfPageData.size - 1) {
                    PageForView(
                        thumbnail = thumbnailOfPageData[(page*2)+1].thumbnail,
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

//@Preview
//@Composable
//private fun PreviewEditBookScreen(
//    viewModel: EditBookViewModel = hiltViewModel(),
//    ) {
//    EditBookScreen()
//}
