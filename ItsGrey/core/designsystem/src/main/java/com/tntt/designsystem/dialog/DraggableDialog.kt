package com.tntt.designsystem.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.icon.IgIcons
import com.tntt.designsystem.theme.IgTheme

@Composable
fun IgDraggableDialog(
    modifier: Modifier = Modifier,
    minValue: Float,
    maxValue: Float,
    initialFontSize: Float,
    changedFontSize: (Float) -> Unit,
) {
    var fontSize by remember { mutableStateOf(initialFontSize) }

    IgTheme {
        Card(
            modifier = modifier.size(400.dp,100.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(25.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "크기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Box(modifier = Modifier.weight(1.5f)) {
                        Text(
                            text = "${fontSize.toInt()}pt",
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.weight(6f)) {
                        Slider(
                            value = fontSize,
                            onValueChange = { fontSize = it; changedFontSize(it)},
                            valueRange = minValue..maxValue,
                            onValueChangeFinished = {},
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.surface,
                                activeTrackColor = MaterialTheme.colorScheme.error,
                                inactiveTrackColor = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(modifier = Modifier.weight(1.0f)) {
                        IgIconButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { fontSize-- ; changedFontSize(fontSize)},
                            icon = {
                                Icon(
                                    imageVector = IgIcons.Remove,
                                    contentDescription = "iconButton"
                                )
                            }
                        )
                    }

                    Box(modifier = Modifier.weight(1.0f)) {
                        IgIconButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { fontSize++ ; changedFontSize(fontSize)},
                            icon = {
                                Icon(
                                    imageVector = IgIcons.Add,
                                    contentDescription = "iconButton"
                                )
                            }
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewDraggableDialog(){
    IgDraggableDialog(
        minValue = 0f,
        maxValue = 256f,
        initialFontSize = 128f,
        changedFontSize = {},
    )
}