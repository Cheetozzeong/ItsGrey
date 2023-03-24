package com.tntt.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.theme.Black

@Composable
fun IgTabMainRow(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
) {
    val color = MaterialTheme.colorScheme.onPrimary
    val shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.primary)
            .drawBehind {
                val borderSize = 4.dp.toPx()
                if (selected)
                {
                    drawLine(
                        color,
                        Offset(borderSize/2, 0f),
                        Offset(borderSize/2, size.height),
                        strokeWidth = borderSize,
                    )
                    drawLine(
                        color,
                        Offset(0f, borderSize/2),
                        Offset(size.width, borderSize/2),
                        strokeWidth = borderSize
                    )
                    drawLine(
                        color,
                        Offset(size.width-borderSize/2,0f),
                        Offset(size.width-borderSize/2, size.height),
                        strokeWidth = borderSize
                    )
                }
                else {
                    drawLine(
                        color,
                        Offset(0f, size.height-borderSize/2),
                        Offset(size.width, size.height-borderSize/2),
                        strokeWidth = borderSize
                    )
                }
            },
        enabled = enabled,
        text = {
            val style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(
                value = style,
                content = {
                    Box(
                        Modifier
                            .padding(vertical = if (selected) 16.dp else 6.dp)
                            .size(width= 100.dp, height = 50.dp)
                            .wrapContentSize(Alignment.Center))

                    {
                        text()
                    }
                },
            )
        },
    )
}




@Composable
fun IgTabsMain(
    titles: List<String>,
){
    var selectedTabIndex by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        titles.forEachIndexed { index, title ->
            IgTabMainRow(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = { Text(text = title) },
                modifier = Modifier.weight(1f),
            )
        }
    }
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
fun TapsMainPreview() {
    val titles = listOf("출간", "작업중", "abc")
    IgTabsMain(titles)
}


@Preview
@Composable
fun TabsTemplatePreview(){
    IgTabsTemplate()
}
