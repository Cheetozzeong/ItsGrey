package com.tntt.viewer

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.designsystem.theme.IgTheme
import itsgrey.feature.viewer.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ViewerTopAppBar() {
    IgTopAppBar(
        title = "BOOK TITLE",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = { /*TODO*/ },
    )
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
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
private fun BookSection(
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
                    it.padding(5.dp, 10.dp)
                } else {
                    it.padding(10.dp, 50.dp)
                }
            },
    ) {page ->
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
@Composable
private fun ViewerScreen(
) {
    IgTheme {
        Scaffold(
            topBar = { ViewerTopAppBar() }
        ) { padding ->
            Column (
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    BookSection()
                }
            }
        }
    }
}

@Preview(name = "vertical", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Composable
private fun PreviewVerticalViewerScreen() {
    ViewerScreen()
}

@Preview(name = "horizontal", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
private fun PreviewHorizontalViewerScreen() {
    ViewerScreen()
}

/*
TODO: 추후에 resource 삭제 요망
* */
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
