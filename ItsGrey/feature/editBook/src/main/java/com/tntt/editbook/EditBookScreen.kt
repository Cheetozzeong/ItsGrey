package com.tntt.editbook

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
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
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgPlusPageButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.dialog.IgDeleteDialog
import com.tntt.designsystem.dialog.IgTitleEditDialog
import com.tntt.designsystem.icon.IgIcons
import com.tntt.editbook.model.Page
import com.tntt.model.*
import com.tntt.ui.PageForView
import kotlinx.coroutines.delay
import org.burnoutcrew.reorderable.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditBookPageRoute(
    viewModel: EditBookViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onViewerClick: (String) -> Unit,
    onPageClick: (String) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.getBook()
    }

    val bookTitle by viewModel.bookTitle.collectAsStateWithLifecycle()
    val thumbnailOfPageDataList by viewModel.thumbnailOfPageData.collectAsStateWithLifecycle()
    val selectedPage by viewModel.selectedPage.collectAsStateWithLifecycle()
    val isCoverExist by viewModel.isCoverExist.collectAsStateWithLifecycle()
    val isPublished by viewModel.isPublished.collectAsStateWithLifecycle()

    val configuration = LocalConfiguration.current

    LaunchedEffect(isPublished) {
        if(isPublished) { onBackClick() }
    }

    Scaffold(
        topBar = {
            EditBookTopAppBar(
                bookTitle = bookTitle,
                onBackClick = { onBackClick() },
                onViewerClick = { onViewerClick(viewModel.bookId) },
                onBookTitleChange = viewModel::titleChange,
                onPublishBookClick = {
                    viewModel.publishBook()
                }
            )
        }
    ) { paddingValues ->

        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {  // 세로
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                MainSectionPortrait(
                    modifier = Modifier.weight(0.75f),
                    thumbnailOfPageDataList = thumbnailOfPageDataList,
                    selectedPage = selectedPage,
                    isCoverExist = isCoverExist,
                    createPage = viewModel::createPage,
                    deletePage = viewModel::deletePage,
                    updateSelectedPage = viewModel::updateSelectedPage,
                    onPageClick = { pageId -> onPageClick(pageId) }
                )
                SideSectionPortrait(
                    modifier = Modifier.weight(0.25f),
                    thumbnailOfPageDataList = thumbnailOfPageDataList,
                    selectedPage = selectedPage,
                    isCoverExist = isCoverExist,
                    updateSelectedPage = viewModel::updateSelectedPage,
                    createPage = viewModel::createPage,
                    movePage = viewModel::movePage,
                    isPageDragEnabled = viewModel::isPageDragEnabled,
                    updatePageOrder = viewModel::updatePageOrder
                )
            }
        } else {    // 가로
            Row(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                SideSectionLandScape(
                    modifier = Modifier.weight(0.25f),
                    thumbnailOfPageDataList = thumbnailOfPageDataList,
                    selectedPage = selectedPage,
                    isCoverExist = isCoverExist,
                    updateSelectedPage = viewModel::updateSelectedPage,
                    createPage = viewModel::createPage,
                    movePage = viewModel::movePage,
                    isPageDragEnabled = viewModel::isPageDragEnabled,
                    updatePageOrder = viewModel::updatePageOrder
                )

                MainSectionLandScape(
                    modifier = Modifier.weight(0.75f),
                    thumbnailOfPageDataList = thumbnailOfPageDataList,
                    selectedPage = selectedPage,
                    isCoverExist = isCoverExist,
                    createPage = viewModel::createPage,
                    deletePage = viewModel::deletePage,
                    updateSelectedPage = viewModel::updateSelectedPage,
                    onPageClick = { pageId -> onPageClick(pageId) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditBookTopAppBar(
    bookTitle: String,
    onBackClick: () -> Unit,
    onViewerClick: () -> Unit,
//    onViewerClick: (String) -> Unit,
    onBookTitleChange: (String) -> Unit,
    onPublishBookClick: () -> Unit,
) {
    var showEditTitleDialog by remember { mutableStateOf(false) }

    if (showEditTitleDialog) {
        IgTitleEditDialog(
            currentTitle = bookTitle,
            onDismiss = { showEditTitleDialog = false },
            onValueChange = { newTitle -> onBookTitleChange(newTitle) }
        )
    }

    IgTopAppBar(
        title = bookTitle,
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() },
        actions = {
            EditBookTitleButton { showEditTitleDialog = true }
            OpenViewerButton { onViewerClick() }
            PublishButton { onPublishBookClick() }
        }
    )
}

@Composable
private fun EditBookTitleButton(
    onClick: () -> Unit
) {
    IgIconButton(
        onClick = { onClick() },
        icon = {
            Icon(
                imageVector = IgIcons.Edit,
                contentDescription = "Title Edit"
            )
        }
    )
}

@Composable
private fun OpenViewerButton(
    onClick: () -> Unit
) {
    IgIconButton(
        onClick = { onClick() },
        icon = {
            Icon(
                imageVector = IgIcons.PreviewTwo,
                contentDescription = "Preview",
            )
        }
    )
}

@Composable
private fun PublishButton(
    onClick: () -> Unit
) {
    IgTextButton(
        onClick = { onClick() },
        text = {
            Text(text = "출간하기!")
        }
    )
}

@Composable
private fun SideSectionLandScape(
    modifier: Modifier,
    thumbnailOfPageDataList: List<Page>,
    selectedPage: Int,
    isCoverExist: Boolean,
    updateSelectedPage: (index: Int) -> Unit,
    createPage: (Boolean) -> Unit,
    movePage: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
//    updatePageOrder: (from: Int, to: Int) -> Unit,
    updatePageOrder: () -> Unit,
) {
    val lazyGridState = rememberReorderableLazyGridState(
        onMove = { from, to ->
            if(isCoverExist) { movePage(from.index, to.index) }
            else {movePage(from.index - 1, to.index - 1)}
        },
        canDragOver = isPageDragEnabled,
        onDragEnd = { from, to -> updatePageOrder() }
    )
    val recColor = MaterialTheme.colorScheme.tertiary

    Column(
        modifier = modifier
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
            if(isCoverExist) {
                item(
                    thumbnailOfPageDataList[0].pageInfo.id,
                    span = { GridItemSpan(2) },
                ) {
                    val cover = thumbnailOfPageDataList[0]

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        PageForView(
                            thumbnail = cover.thumbnail,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clickable { updateSelectedPage(cover.pageInfo.order) }
                                .border(
                                    width = 4.dp,
                                    color = if (selectedPage == cover.pageInfo.order) MaterialTheme.colorScheme.outline else Color.Transparent
                                )
                                .shadow(0.5.dp)
                        )
                        Text(
                            text = "표지",
                            modifier = Modifier
                                .padding(top = 8.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }else {
                item(
                    span = { GridItemSpan(2) },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AddPageButton(
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) { createPage(true) }
                        Text(
                            text = "표지",
                            modifier = Modifier
                                .padding(top = 8.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            items(
                items = if(isCoverExist) thumbnailOfPageDataList.drop(1) else thumbnailOfPageDataList,
                key = { Page -> Page.pageInfo.id }
            ) {item ->

                ReorderableItem(lazyGridState, item.pageInfo.id) { isDragging ->
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
                                .clickable { updateSelectedPage(item.pageInfo.order) }
                                .border(
                                    width = 4.dp,
                                    color = if (selectedPage == item.pageInfo.order) MaterialTheme.colorScheme.outline else Color.Transparent
                                )
                                .shadow(0.5.dp)
                        )
                        Text(
                            text = "${item.pageInfo.order}",
                            modifier = Modifier
                                .padding(top = 8.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            item {
                IgPlusPageButton(
                    onClick = { createPage(false) },
                    text = "New Page",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxSize(0.9f)
                )
            }
        }
    }
}


@Composable
private fun SideSectionPortrait(
    modifier: Modifier,
    thumbnailOfPageDataList: List<Page>,
    selectedPage: Int,
    isCoverExist: Boolean,
    updateSelectedPage: (index: Int) -> Unit,
    createPage: (Boolean) -> Unit,
    movePage: (from: Int, to: Int) -> Unit,
    isPageDragEnabled: (ItemPosition, ItemPosition) -> Boolean,
//    updatePageOrder: (from: Int, to: Int) -> Unit,
    updatePageOrder: () -> Unit,
) {

    val lazyListState = rememberReorderableLazyListState(
        onMove = { from, to -> movePage(from.index, to.index) },
        canDragOver = isPageDragEnabled,
        onDragEnd = { from, to -> updatePageOrder() }
    )
    val recColor = MaterialTheme.colorScheme.tertiary

    Row(
        modifier = modifier
    ) {
        if(!isCoverExist) { AddPageButton { createPage(true) } }

        LazyRow(
            state = lazyListState.listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .then(Modifier.reorderable(lazyListState))
                .detectReorderAfterLongPress(lazyListState),
        ) {
            items(
                thumbnailOfPageDataList,
                key = { Page -> Page.pageInfo.id }
            ) { item ->
                ReorderableItem(
                    state = lazyListState,
                    key = item.pageInfo.id
                ) { isDragging ->
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
                                    updateSelectedPage(item.pageInfo.order)
                                }
                                .border(
                                    width = 4.dp,
                                    color = if (selectedPage == item.pageInfo.order) MaterialTheme.colorScheme.outline else Color.Transparent
                                )
                                .shadow(0.5.dp)
                        )
                        Text(
                            text = "${if(item.pageInfo.order == 0) "표지" else item.pageInfo.order}",
                            modifier = Modifier
                                .wrapContentSize(Alignment.BottomCenter),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            item {
                IgPlusPageButton(
                    onClick = { createPage(false) },
                    text = "새 페이지",
                    modifier = Modifier.fillMaxSize(0.9f)
                )
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainSectionLandScape(
    modifier: Modifier,
    thumbnailOfPageDataList: List<Page>,
    createPage: (Boolean) -> Unit,
    deletePage: (String) -> Unit,
    isCoverExist: Boolean,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    onPageClick: (String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = selectedPage)
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deletePageId by remember { mutableStateOf("") }

    if (showDeleteDialog) {
        IgDeleteDialog(
            onDelete = {
                deletePage(deletePageId)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    LaunchedEffect(pagerState.currentPage) {
        if(thumbnailOfPageDataList.isEmpty()) return@LaunchedEffect
        if(selectedPage in pagerState.currentPage * 2 - 1 .. pagerState.currentPage * 2) return@LaunchedEffect
        if(isCoverExist) {
            if(pagerState.currentPage == 0) updateSelectedPage(0)
            else updateSelectedPage(thumbnailOfPageDataList[pagerState.currentPage * 2 - 1].pageInfo.order)
        }else {
            if(pagerState.currentPage == 0) return@LaunchedEffect
            updateSelectedPage(thumbnailOfPageDataList[pagerState.currentPage * 2 - 2].pageInfo.order)
        }
    }

    LaunchedEffect(selectedPage) {
        pagerState.scrollToPage((selectedPage + 1) / 2)
    }

    HorizontalPager(
        state = pagerState,
        count = if(thumbnailOfPageDataList.isEmpty()) 2 else if(isCoverExist) (thumbnailOfPageDataList.size) / 2 + 1 else thumbnailOfPageDataList.size / 2 + 2,
        modifier = modifier
            .fillMaxSize()
            .padding(9.dp, 50.dp)
    ) { currentPageIndex ->
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            when(currentPageIndex) {
                0 -> { // 표지 페이지
                    if(!isCoverExist) {
                        AddPageButton { createPage(true) }
                    }else {
                        val curPage = thumbnailOfPageDataList[currentPageIndex]

                        Box(
                            modifier = Modifier
                                .padding(0.dp, 36.dp)
                                .shadow(1.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            PageForView(
                                thumbnail = curPage.thumbnail,
                                modifier = Modifier.clickable { onPageClick(curPage.pageInfo.id) }
                            )
                            DeletePageButton(
                                modifier = Modifier.align(TopEnd),
                                onClick = {
                                    deletePageId = curPage.pageInfo.id
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
                else -> {
                    for (i in 0..1) {
                        val isOutOfBound = if(isCoverExist) {
                                                (currentPageIndex * 2) - 1 + i >= thumbnailOfPageDataList.size
                                            }else {
                                                (currentPageIndex * 2) - 1 + i > thumbnailOfPageDataList.size
                                            }
                        if(isOutOfBound) {
                            Box(
                                Modifier.weight(1f)
                            ) {
                                AddPageButton { createPage(false) }
                            }
                            break
                        }else {

                            val curPage = thumbnailOfPageDataList[
                                if(isCoverExist) (currentPageIndex) * 2 - 1 + i
                                else (currentPageIndex) * 2 - 2 + i
                            ]


                            Box(
                                Modifier
                                    .fillMaxHeight()
                            ) {
                                PageForView(
                                    thumbnail = curPage.thumbnail,
                                    modifier = Modifier
                                        .padding(9.dp)
                                        .shadow(1.dp)
                                        .clickable { onPageClick(curPage.pageInfo.id) }
                                )
                                DeletePageButton(
                                    modifier = Modifier
                                        .align(TopEnd),
                                    onClick = {
                                        deletePageId = curPage.pageInfo.id
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainSectionPortrait(
    modifier: Modifier,
    thumbnailOfPageDataList: List<Page>,
    createPage: (Boolean) -> Unit,
    deletePage: (String) -> Unit,
    isCoverExist: Boolean,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    onPageClick: (String) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = selectedPage)
    var deletePageId by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        IgDeleteDialog(
            onDelete = {
                deletePage(deletePageId)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage >= thumbnailOfPageDataList.size) return@LaunchedEffect
        if(thumbnailOfPageDataList.isNotEmpty()) {
            updateSelectedPage(thumbnailOfPageDataList[pagerState.currentPage].pageInfo.order)
        }
    }
    LaunchedEffect(selectedPage) {
        pagerState.scrollToPage(
            if(isCoverExist) selectedPage
            else selectedPage - 1
        )
    }

    HorizontalPager(
        state = pagerState,
        count = thumbnailOfPageDataList.size + 1,
        modifier = modifier
            .fillMaxSize()
            .padding(9.dp, 16.dp),
    ) { currentPageIndex ->
        when (currentPageIndex) {
            0 -> { // 첫 페이지
                if(!isCoverExist) {
                    AddPageButton { createPage(true) }
                }else {
                    val curPage = thumbnailOfPageDataList[currentPageIndex]

                    Box(
                        modifier = Modifier
                            .padding(0.dp, 36.dp)
                            .shadow(1.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        PageForView(
                            thumbnail = curPage.thumbnail,
                            modifier = Modifier.clickable { onPageClick(curPage.pageInfo.id) }
                        )
                        DeletePageButton(
                            modifier = Modifier.align(TopEnd),
                            onClick = {
                                deletePageId = curPage.pageInfo.id
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
            thumbnailOfPageDataList.size -> { // 마지막 페이지
                AddPageButton { createPage(false) }
            }
            else -> {

                val curPage = thumbnailOfPageDataList[currentPageIndex]

                Box(
                    modifier = Modifier
                        .padding(0.dp, 36.dp)
                        .shadow(1.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    PageForView(
                        thumbnail = curPage.thumbnail,
                        modifier = Modifier.clickable { onPageClick(curPage.pageInfo.id) }
                    )
                    DeletePageButton(
                        modifier = Modifier.align(TopEnd),
                        onClick = {
                            deletePageId = curPage.pageInfo.id
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AddPageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        IgPlusPageButton(
            onClick = { onClick() },
            text = "",
            modifier = Modifier.padding(9.dp)
        )
    }
}

@Composable
private fun DeletePageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IgIconButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = {
            Icon(
                IgIcons.Close,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.error
            )
        }
    )
}
