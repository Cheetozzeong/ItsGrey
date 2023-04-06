package com.tntt.designsystem.dialog


import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tntt.designsystem.theme.IgTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IgTitleEditDialog(
    currentTitle: String,
    onDismiss: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val (title, setTitle) = remember { mutableStateOf(currentTitle) }

    IgTheme() {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                OutlinedTextField(
                    value = title,
                    onValueChange = { newTitle ->
                        setTitle(newTitle)
                        onValueChange(newTitle)
                    },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                    },
                ) {
                    Text(
                        text = "완료",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                ) {
                    Text(
                        text = "취소",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
        )
    }
}