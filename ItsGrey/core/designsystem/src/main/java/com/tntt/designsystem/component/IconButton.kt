package com.tntt.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.icon.IgIcons

@Composable
fun IgIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Unspecified,
            contentColor = Color.Black
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun IgIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
) {
    IgIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = ButtonDefaults.TextButtonContentPadding,
    ) {
        IgButtonContent(
            Icon = icon,
        )
    }
}

@Composable
private fun IgButtonContent(
    Icon: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.padding(start = 0.dp)
    ) {
        Icon()
    }
}


@Preview
@Composable
fun PreviewIconButton(){
    IgIconButton(
        onClick = {},
        icon = {
            Icon(
                painter = painterResource(id = IgIcons.Add),
                contentDescription = "iconButton"
            )
        }
    )
}
