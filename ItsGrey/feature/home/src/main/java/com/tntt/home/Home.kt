package com.tntt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.theme.IgTheme
import com.tntt.designsystem.component.IgTabsMain
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.home.model.User
import com.tntt.model.*
import itsgrey.feature.home.R
import java.util.*

private enum class TabPage {
    Published, Working
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun Home(modifier: Modifier = Modifier) {

    // fake
    val user = User(name = "fakeUser",id = "id")

    var tabPage by remember { mutableStateOf(TabPage.Published) }

    IgTheme {
        val colorBackground = MaterialTheme.colorScheme.surface
        val borderColor = MaterialTheme.colorScheme.onPrimary

        Scaffold(
            modifier = Modifier,
            topBar = { IgTopAppBar(
                modifier = Modifier.padding(horizontal = 25.dp),
                title = stringResource(id = R.string.home_toolbar_name),
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
                    val titles = listOf("출판","작업중")
                    IgTabsMain(titles = titles, selectedTabIndex = {
                            if(it == 0) tabPage = TabPage.Published
                            else if (it == 1) tabPage = TabPage.Working
                        }
                    )
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
                                Offset(0.dp.toPx(), size.height - borderSize / 2),
                                Offset(size.width, size.height - borderSize / 2),
                                strokeWidth = borderSize
                            )
                            drawLine(
                                borderColor,
                                Offset(size.width - borderSize / 2, size.height),
                                Offset(size.width - borderSize / 2, -3.dp.toPx()),
                                strokeWidth = borderSize
                            )
                        }
                ) {
                    BookList(modifier, tabPage)
                }
            }
        }
    }
}

@Composable
private fun BookList(
    modifier: Modifier = Modifier,
    tabPage: TabPage
) {
    IgTheme() {
        var text = if (tabPage == TabPage.Published) "출판됨" else "안댐"
        Text(text = text, textAlign = TextAlign.Center)

    }
}