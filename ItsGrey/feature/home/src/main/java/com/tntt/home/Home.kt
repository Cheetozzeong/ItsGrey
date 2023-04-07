package com.tntt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.tntt.designsystem.component.IgPlusPageButton
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

@Composable
internal fun HomePageRoute(
    onThumbnailClickForEdit: (String) -> Unit,
    onThumbnailClickForView: (String) -> Unit,
    viewModel: HomePageViewModel = hiltViewModel(),
){
    LaunchedEffect(Unit) {
        viewModel.getWorkingBookList()
        viewModel.getPublishedBookList()
    }

    HomePageScreen(
        onNewButtonClick = viewModel::createBook,
        onThumbnailClick = { id, type ->
            when(type) {
                TabPage.Published.name -> { onThumbnailClickForView(id) }
                TabPage.Working.name -> { onThumbnailClickForEdit(id) }
            }
        },
        viewModel = viewModel,
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomePageScreen(
    modifier: Modifier = Modifier,
    onNewButtonClick: () -> Unit,
    onThumbnailClick: (String, String) -> Unit,
    viewModel: HomePageViewModel,
) {

    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val user = User(name = viewModel.userName,id = viewModel.userId)

    val publishedBookList by viewModel.publishedBookList.collectAsState()
    val workingBookList by viewModel.workingBookList.collectAsState()

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
                title = stringResource(R.string.home_toolbar_name),
                actions = {
                    Text(
                        modifier = Modifier.pointerInput(Unit) {
                           detectTapGestures {
                                viewModel.getPublishedBookList()
                                viewModel.getWorkingBookList()
                           }
                        },
                        text = "${user.name}님의 서재"
                    )
                }
            ) }
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
                        onThumbnailClick = { id ->
                            onThumbnailClick(id, tabPage.name)
                        },
                        onNewButtonClick = onNewButtonClick
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
    onThumbnailClick: (String) -> Unit,
    onNewButtonClick: () -> Unit
) {
    val windowSize = computeWindowSizeClasses(screenWidth)
    IgTheme {
        if (books.isEmpty() && tabPage == TabPage.Published) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = stringResource(R.string.homeNullListMessage),
                    style = MaterialTheme.typography.displayMedium)
            }
        } else {
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
                            onThumbnailClick(it)
                        }
                    )
                }
                if (tabPage == TabPage.Working) {
                    item {
                        IgPlusPageButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onNewButtonClick() },
                            text = "New..."
                        )
                    }
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
                                onClick = { onThumbnailClick(bookId) } /*TODO 네비 연*/
                            )
                    ) {
                        PageForView(modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                            , thumbnail = book.thumbnail!!)
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
    if (seconds in 1..59) {
        Text("${seconds}초 전")
        return
    } else if (seconds < 0) {
        Text("미래로는 갈 수 업어용")
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