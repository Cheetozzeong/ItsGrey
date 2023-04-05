package com.tntt.designsystem.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.tntt.designsystem.theme.IgTheme

//@Preview
//@Composable
//private fun PreviewDeleteDialog() {
//    DeleteDialog() {}
//}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    IgTheme() {
        AlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .widthIn(max = 300.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text = "페이지를 삭제할 건가요?",
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            confirmButton = {
                Button(
                    onClick = { /*TODO*/ },
                ) {
                    Text(
                        text = "네",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                ) {
                    Text(
                        text = "아니요",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
        )
    }
}