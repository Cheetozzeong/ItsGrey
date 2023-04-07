package com.tntt.viewer

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import itsgrey.feature.viewer.R
import org.burnoutcrew.reorderable.*
import kotlin.math.ceil


@Composable
internal fun ViewerPageRoute(
    viewModel: ViewerViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val bookTitle by viewModel.bookTitle.collectAsStateWithLifecycle()
    val thumbnailOfPageDataList by viewModel.thumbnailOfPageData.collectAsStateWithLifecycle()
    val selectedPage by viewModel.selectedPage.collectAsStateWithLifecycle()

    EditBookScreen(
        bookTitle = bookTitle,
        thumbnailOfPageDataList = thumbnailOfPageDataList.toMutableList(),
        selectedPage = selectedPage,
        updateSelectedPage = viewModel::updateSelectedPage,
        onBackClick = onBackClick,
    )
}

@Composable
private fun EditBookScreen(
    bookTitle: String,
    thumbnailOfPageDataList: MutableList<Page>,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        VerticalModeScreen(bookTitle, thumbnailOfPageDataList, selectedPage, updateSelectedPage, onBackClick)
    } else {
        HorizontalModeScreen(bookTitle, thumbnailOfPageDataList, selectedPage, updateSelectedPage, onBackClick)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewerTopAppBar(
    bookTitle: String,
    onBackClick: () -> Unit,
) {
    IgTopAppBar(
        title = bookTitle,
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = {/*TODO : onBackClick -> save 후 이동 */
            // 1. save
            onBackClick()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerticalModeScreen(
    bookTitle: String,
    thumbnailOfPageDataList: MutableList<Page>,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    onBackClick: () -> Unit,
) {
    IgTheme {
        Scaffold(
            topBar = { ViewerTopAppBar(bookTitle, onBackClick) }
        ) { padding ->
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    MainSection(thumbnailOfPageDataList, selectedPage, updateSelectedPage)
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
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
    onBackClick: () -> Unit,
) {
    IgTheme {
        Scaffold(
            topBar = { ViewerTopAppBar(bookTitle, onBackClick) }
        ) { padding ->
            Row(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    MainSection(thumbnailOfPageDataList, selectedPage, updateSelectedPage)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainSection(
    thumbnailOfPageDataList: MutableList<Page>,
    selectedPage: Int,
    updateSelectedPage: (index: Int) -> Unit,
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

    if (thumbnailOfPageDataList.size == 0) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = stringResource(R.string.waitMessage),
                style = MaterialTheme.typography.displayMedium)
        }
    }
    else {
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
                        it.padding(horizontal = 10.dp)
                    } else {
                        it.padding(vertical = 10.dp)
                    }
                },
        ) { page ->

            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Box(modifier = Modifier) {
                    PageForView(
                        thumbnail = thumbnailOfPageDataList[page].thumbnail,
                        modifier = modifier.background(MaterialTheme.colorScheme.surface)
                    )
                }
            } else {
                Row(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PageForView(
                        thumbnail = thumbnailOfPageDataList[page * 2].thumbnail,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.primary)
                    )
                    if ((page * 2) + 1 <= thumbnailOfPageDataList.size - 1) {
                        PageForView(
                            thumbnail = thumbnailOfPageDataList[(page * 2) + 1].thumbnail,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.primary)
                        )
                    } else {
                        Box(
                            Modifier.aspectRatio(2f / 3f)
                                .background(MaterialTheme.colorScheme.secondary)
                        )
                    }
                }
            }
        }
    }
}