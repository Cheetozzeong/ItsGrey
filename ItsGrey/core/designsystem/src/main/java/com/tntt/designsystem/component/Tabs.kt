package com.tntt.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tntt.core.designsystem.theme.Black

@Composable
fun IgTabMain(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier.background(MaterialTheme.colorScheme.primary),
        enabled = enabled,
        text = {
            val style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(
                value = style,
                content = {
                    Box {
                        text()
                    }
                },
            )
        },
    )
}

@Composable
fun IgTabMainRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        indicator = { tabPositions ->
            IgTabMainIndicator(tabPosition = tabPositions, index = selectedTabIndex)

        },
        tabs = tabs,
    )
}



@Composable
fun IgTabMainIndicator(
    tabPosition: List<TabPosition>, index: Int
) {
    val width = tabPosition[index].width
    val offsetStart = tabPosition[index].left
    Box(
        Modifier
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = offsetStart)
            .width(width)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
            )
    )
}
@Composable
private fun IgTabsTemplateIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            ),
    )
}

@Composable
private fun TabsTemplateItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            Black
        },
        animationSpec = tween(easing = LinearEasing),
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun TabTemplate(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 100.dp,
    onClick: (index: Int) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        IgTabsTemplateIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = MaterialTheme.colorScheme.primary,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                TabsTemplateItem(
                    isSelected = isSelected,
                    onClick = {
                        onClick(index)
                    },
                    tabWidth = tabWidth,
                    text = text,
                )
            }
        }
    }
}

@Composable
fun IgTabsTemplate() {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }

    TabTemplate(
        items = listOf("표지", "페이지"),
        selectedItemIndex = selected,
        onClick = setSelected,
    )
}

@Preview
@Composable
fun TabsMainPreview(){
    var selectedTabIndex by remember { mutableStateOf(0) }
    val titles = listOf("출간", "작업중")
    IgTabMainRow(selectedTabIndex = selectedTabIndex,
        modifier = Modifier
    ) {
        titles.forEachIndexed { index, title ->
            IgTabMain(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = { Text(text = title) },
            )
        }
    }
}

@Preview
@Composable
fun TabsTemplatePreview(){
    IgTabsTemplate()
}
