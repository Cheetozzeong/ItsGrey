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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tntt.designsystem.component.IgPlusPageButton
import com.tntt.designsystem.theme.IgTheme
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.dialog.IgTitleEditDialog
import com.tntt.designsystem.icon.IgIcons
import com.tntt.editbook.model.Page
import com.tntt.model.*
import com.tntt.ui.PageForView
import org.burnoutcrew.reorderable.*
import kotlin.math.ceil


@Composable
internal fun EditBookPageRoute(
    viewModel: EditBookViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onNewPageClick: () -> Unit,
) {
    val bookTitle by viewModel.bookTitle.collectAsStateWithLifecycle()
    val thumbnailOfPageDataList by viewModel.thumbnailOfPageData.collectAsStateWithLifecycle()
    val selectedPage by viewModel.selectedPage.collectAsStateWithLifecycle()
    val isCoverExist by viewModel.isCoverExist.collectAsStateWithLifecycle()

    EditBookScreen(
        bookTitle = bookTitle,
        thumbnailOfPageDataList = thumbnailOfPageDataList.toMutableList(),
        isCoverExist = isCoverExist,
        movePage = viewModel::movePage,
        selectedPage = selectedPage,
        updateSelectedPage = viewModel::updateSelectedPage,
        updatePageOrder = viewModel::updatePageOrder,
        isPageDragEnabled = viewModel::isPageDragEnabled,
        onBackClick = onBackClick,
        onViewerClick = onViewerClick,
        onNewPageClick = onNewPageClick,
    )
}

@Composable
private fun EditBookScreen(
    bookTitle: String,
    thumbnailOfPageDataList: MutableList<Page>,
    isCoverExist: Boolean,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    updatePageOrder: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onNewPageClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        VerticalModeScreen(bookTitle, thumbnailOfPageDataList, isCoverExist, movePage, selectedPage, updateSelectedPage, updatePageOrder, isPageDragEnabled, onBackClick, onViewerClick, onNewPageClick)
    } else {
        HorizontalModeScreen(bookTitle, thumbnailOfPageDataList, isCoverExist, movePage, selectedPage, updateSelectedPage, updatePageOrder, isPageDragEnabled, onBackClick, onViewerClick, onNewPageClick)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditBookTopAppBar(
    bookTitle: String,
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
) {
    var showEditTitleDialog by remember { mutableStateOf(false) }
    IgTopAppBar(
        title = bookTitle,
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = {/*TODO : onBackClick -> save 후 이동 */
            // 1. save
            onBackClick()
        },
        actions = {
            IconButton(
                onClick = { showEditTitleDialog = true },
                modifier = Modifier
                    .padding(8.dp, 0.dp)
            ) {
                Icon(
                    imageVector = IgIcons.Edit,
                    contentDescription = "Title Edit",
                    tint = Color.Black,
                )
            }
            if (showEditTitleDialog) {
                IgTitleEditDialog(currentTitle = bookTitle, onTitleChanged = {}, onDismiss = { showEditTitleDialog = false } )
            }
            IconButton(
                onClick = { /*TODO : onViewerClick -> save 후 이동 */ },
                modifier = Modifier
                    .padding(8.dp, 0.dp)
            ) {
                Icon(
                    imageVector = IgIcons.PreviewTwo,
                    contentDescription = "Preview",
                    tint = Color.Black,
                )
            }
            Button(
                onClick = { /*TODO : (publish) onBackClick -> save 후 이동 */ },
                modifier = Modifier
                    .padding(8.dp, 0.dp)
            ) {
                Text(
                    text = "출간하기!",
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerticalModeScreen(
    bookTitle: String,
    thumbnailOfPageDataList: MutableList<Page>,
    isCoverExist: Boolean,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    updatePageOrder: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onNewPageClick: () -> Unit,
) {
    IgTheme {
        Scaffold(
            topBar = { EditBookTopAppBar(bookTitle, onBackClick, onViewerClick) }
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
                    MainSection(thumbnailOfPageDataList, isCoverExist, selectedPage, updateSelectedPage, updatePageOrder, onNewPageClick)
                }
                Box(modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.tertiary)
                ) {
                    VerticalModeSideSection(thumbnailOfPageDataList, isCoverExist, movePage, selectedPage, updateSelectedPage, updatePageOrder, isPageDragEnabled, onNewPageClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalModeScreen(
    bookTitle: String,
    thumbnailOfPageDataList: MutableList<Page>,
    isCoverExist: Boolean,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    updatePageOrder: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onNewPageClick: () -> Unit,
) {
    val recColor = MaterialTheme.colorScheme.tertiary
    IgTheme {
        Scaffold(
            topBar = { EditBookTopAppBar(bookTitle, onBackClick, onViewerClick) }
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
                    HorizontalModeSideSection(thumbnailOfPageDataList, isCoverExist, movePage, selectedPage, updateSelectedPage, updatePageOrder, isPageDragEnabled, onNewPageClick)
                }
                Box(
                    modifier = Modifier
                        .weight(0.75f)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    MainSection(thumbnailOfPageDataList, isCoverExist, selectedPage, updateSelectedPage, updatePageOrder, onNewPageClick)
                }
            }
        }
    }
}

@Composable
private fun VerticalModeSideSection(
    thumbnailOfPageDataList: MutableList<Page>,
    isCoverExist: Boolean,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    updatePageOrder: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
    onNewPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberReorderableLazyListState(
        onMove = { from, to -> movePage(from.index, to.index) },
        canDragOver = isPageDragEnabled,
        onDragEnd = { from, to -> updatePageOrder(from, to)
        })
    val recColor = MaterialTheme.colorScheme.tertiary
    Row {
        if (!isCoverExist) {
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxSize(0.9f)
                    .padding(8.dp, 16.dp, 8.dp, 16.dp),
                contentAlignment = Alignment.Center
            ) {
                IgPlusPageButton(
                    onClick = { /* TODO */ },
                    text = "표지 만들기",
                    modifier = modifier,
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(if (!isCoverExist) 0.75f else 1f)
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        color = if (!isCoverExist) recColor else Color.Transparent,
                        topLeft = Offset(0f, 0f),
                        size = Size(1.dp.toPx(), size.height),
                    )
                },
        ) {
            LazyRow(
                state = lazyListState.listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 16.dp, 16.dp, 16.dp)
                    .then(Modifier.reorderable(lazyListState))
                    .detectReorderAfterLongPress(lazyListState),
            ) {
                items(thumbnailOfPageDataList, { Page -> Page.pageInfo.order }) { item ->
                    ReorderableItem(lazyListState, item.pageInfo.order) { isDragging ->
                        val scale = animateFloatAsState(if (isDragging) 1.1f else 1.0f)
                        val elevation = animateDpAsState(if (isDragging) 4.dp else 0.dp)
                        Column(
                            modifier = Modifier
                                .padding(16.dp, 0.dp)
                                .scale(scale.value)
                                .shadow(elevation.value),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            PageForView(
                                thumbnail = item.thumbnail,
                                modifier = Modifier
                                    .fillMaxSize(0.9f)
                                    .clickable {
                                        updateSelectedPage(thumbnailOfPageDataList.indexOf(item))
                                    }
                                    .border(
                                        width = 4.dp,
                                        color = if (selectedPage == thumbnailOfPageDataList.indexOf(
                                                item
                                            )
                                        ) MaterialTheme.colorScheme.outline else Color.Transparent
                                    )
                                    .shadow(0.5.dp)
                            )
                            Text(
                                text = "${item.pageInfo.order}",
                                modifier = Modifier
                                    .wrapContentSize(Alignment.BottomCenter),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HorizontalModeSideSection(
    thumbnailOfPageDataList: MutableList<Page>,
    isCoverExist: Boolean,
    movePage: (from: Int, to: Int) -> Unit,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    updatePageOrder: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
    onNewPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyGridState = rememberReorderableLazyGridState(
        onMove = { from, to -> movePage(from.index, to.index) },
        canDragOver = isPageDragEnabled,
        onDragEnd = { from, to -> updatePageOrder(from, to)
        })
    val recColor = MaterialTheme.colorScheme.tertiary

    LaunchedEffect(lazyGridState) {

    }

    Column {
        if (!isCoverExist) {
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .align(CenterHorizontally)
                    .padding(8.dp, 16.dp),
                contentAlignment = Alignment.Center
            ) {
                IgPlusPageButton(
                    onClick = { /* TODO */ },
                    text = "표지 만들기",
                    modifier = modifier
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(if (!isCoverExist) 0.75f else 1f)
                .fillMaxWidth()
                .padding(8.dp)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        color = if (!isCoverExist) recColor else Color.Transparent,
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, 1.dp.toPx()),
                    )
                },
        ) {
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
                items(thumbnailOfPageDataList, { Page -> Page.pageInfo.order }) { item ->
                    ReorderableItem(lazyGridState, item.pageInfo.order) { isDragging ->
                        val scale = animateFloatAsState(if (isDragging) 1.1f else 1.0f)
                        val elevation = animateDpAsState(if (isDragging) 4.dp else 0.dp)
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .scale(scale.value)
                                .shadow(elevation.value),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            PageForView(
                                thumbnail = item.thumbnail,
                                modifier = Modifier
                                    .clickable {
                                        updateSelectedPage(thumbnailOfPageDataList.indexOf(item))
                                    }
                                    .border(
                                        width = 4.dp,
                                        color = if (selectedPage == thumbnailOfPageDataList.indexOf(
                                                item
                                            )
                                        ) MaterialTheme.colorScheme.outline else Color.Transparent
                                    )
                                    .shadow(0.5.dp)
                            )
                            Text(
                                text = "${item.pageInfo.order}",
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainSection(
    thumbnailOfPageDataList: MutableList<Page>,
    isCoverExist: Boolean,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    updatePageOrder: (from: Int, to: Int) -> Unit,
    onNewPageClick: () -> Unit,
    modifier: Modifier = Modifier
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
            thumbnailOfPageDataList.size
        } else {
            ceil(thumbnailOfPageDataList.size.toFloat() / 2.0).toInt()
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
            Box (modifier = Modifier
                .padding(0.dp, 36.dp)
                .shadow(1.dp)
            ) {
                PageForView(
                    thumbnail = thumbnailOfPageDataList[page].thumbnail,
                    modifier = modifier
                )
                IconButton(
                    onClick = { /* TODO : deleteDialog 열기 */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        IgIcons.Close,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .size(40.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        } else {
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(1f)
                ) {
                    PageForView(
                        thumbnail = thumbnailOfPageDataList[page * 2].thumbnail,
                        modifier = Modifier
                            .padding(9.dp)
                            .shadow(1.dp)
                    )
                    IconButton(
                        onClick = { /* TODO : deleteDialog 열기 */ },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            IgIcons.Close,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(30.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                if ((page * 2) + 1 <= thumbnailOfPageDataList.size - 1) {
                    Box(modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(1f)
                    ) {
                        PageForView(
                            thumbnail = thumbnailOfPageDataList[(page * 2) + 1].thumbnail,
                            modifier = Modifier
                                .padding(9.dp)
                                .shadow(1.dp)
                        )
                        IconButton(
                            onClick = { /* TODO : deleteDialog 열기 */ },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                IgIcons.Close,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .size(30.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}