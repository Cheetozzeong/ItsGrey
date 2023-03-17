package com.tntt.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.icon.IgIcons

@Composable
fun IgTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun IgTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
) {
    IgTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = ButtonDefaults.TextButtonContentPadding
    ) {
        IgTextButtonContent(
            text = text,
        )
    }
}

@Composable
private fun IgTextButtonContent(
    text: @Composable () -> Unit,
) {
    Box(
        Modifier
            .padding(
                start = 0.dp
            )
    )
    text()
}

@Preview
@Composable
fun PreviewTextButton(){
    IgTextButton(
        onClick = {},
        text = { Text(text = "Text") }
    )
}
