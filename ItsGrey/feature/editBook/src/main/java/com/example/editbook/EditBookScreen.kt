package com.example.editbook

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.theme.IgTheme
import com.tntt.designsystem.icon.IgIcons
import itsgrey.feature.editbook.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditBookTopAppBar() {
    IgTopAppBar(
        title = stringResource(id = android.R.string.untitled),
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(ThumbnailOfPageData) { item ->
            ThumbnailOfPage(
                drawable = item,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}

@Composable
fun ThumbnailOfPage(
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
                TODO 추후에 resource 삭제 요망
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

@Preview
@Composable
private fun MainSection(
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(9.dp, 50.dp),
        verticalAlignment = Alignment.CenterVertically,
        ) {
        items(ThumbnailOfPageData) { item ->
            ThumbnailOfPage(
                drawable = item,
                modifier = Modifier
                    .fillParentMaxWidth(0.5f)
                    .padding(9.dp)
                    .fillMaxHeight()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun EditBookScreen(modifier: Modifier = Modifier) {
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