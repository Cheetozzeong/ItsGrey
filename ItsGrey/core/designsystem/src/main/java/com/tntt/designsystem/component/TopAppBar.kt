package com.tntt.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.tntt.designsystem.icon.IgIcons

/**
 * navigationIcon 이 없을 때 사용 (home 화면에서만 사용)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IgTopAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(),
) {
    TopAppBar(
        title = { Text(text = title)},
        actions = actions,
        colors = colors,
        modifier = modifier.testTag("igTopAppBar"),
    )
}

/**
 * actions 가 없을 때 사용 (BookView 화면에서만 사용)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IgTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("igTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IgTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier.testTag("igTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IgCenterTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier.testTag("igTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Main View Top App Bar")
@Composable
private fun PreviewMainViewTopAppBar() {
    IgTopAppBar(
        title = "untitled",
        actions = {
            Text(text = "UnKnown")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Make View Top App Bar")
@Composable
private fun PreviewMakeViewTopAppBar() {
    IgTopAppBar(
        title = "title",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Before",
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.PreviewTwo,
                    contentDescription = "Preview",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Edit View Top App Bar")
@Composable
private fun PreviewEditViewTopAppBar() {
    IgTopAppBar(
        title = "title",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Before",
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.Template,
                    contentDescription = "Template",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.Check,
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Gallery View Top App Bar")
@Composable
private fun PreviewGalleryViewTopAppBar() {
    IgCenterTopAppBar(
        title = "title",
        navigationIcon = IgIcons.Close,
        navigationIconContentDescription = "Close",
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.ArrowForward,
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Image Edit View Top App Bar")
@Composable
private fun PreviewImageEditViewTopAppBar() {
    IgTopAppBar(
        title = "title",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Before",
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.Undo,
                    contentDescription = "Undo",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.Redo,
                    contentDescription = "Redo",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = IgIcons.Check,
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Book View Top App Bar")
@Composable
private fun PreviewBookViewTopAppBar() {
    IgTopAppBar(
        title = "title",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Before",
    )
}