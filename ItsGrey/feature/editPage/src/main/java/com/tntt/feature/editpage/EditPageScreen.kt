package com.tntt.feature.editpage

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.designsystem.theme.IgTheme
import com.tntt.model.BoxData
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail
import com.tntt.ui.PageForEdit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(
    modifier: Modifier,
    thumbnail: Thumbnail
) {
    Scaffold(
        topBar = {EditBookTopAppBar()},
    ) { paddingValues ->

        val isFontSizeDialogShown = remember { mutableStateOf(false) }

        Column(
            Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                CreateImageBoxButton()
                CreateTextBoxButton()
                OpenFontSizeDialogButton(
                    fontSize = 0,
                    openDialog = { isFontSizeDialogShown.value = true }
                )
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                PageForEdit(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(20.dp)
                        .background(MaterialTheme.colorScheme.background),
                    thumbnail = thumbnail
                )
            }
        }

        if(isFontSizeDialogShown.value) {
            RegulateFontSizeDialog()
        }
    }
}

@Composable
private fun CreateImageBoxButton() {
    IgIconButton(
        onClick = { /*TODO*/ },
        icon = {
            Icon(
                imageVector = IgIcons.AddImageBox,
                contentDescription = "AddImageBox"
            )
        },
    )
}

@Composable
private fun CreateTextBoxButton() {
    IgIconButton(
        onClick = { /*TODO*/ },
        icon = {
            Icon(
                imageVector = IgIcons.AddTextBox,
                contentDescription = "AddTextBox"
            )
        },
    )
}

@Composable
private fun OpenFontSizeDialogButton(fontSize: Int, openDialog: () -> Unit) {
    IgTextButton(
        onClick = { openDialog() },
        content = {
            Text(text = "$fontSize pt")
        }
    )
}

@Composable
private fun RegulateFontSizeDialog() {

    Dialog(onDismissRequest = { /*TODO*/ }) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditBookTopAppBar() {
    IgTopAppBar(
        titleRes = "",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = { /*TODO*/ },
        actions = {
            IgIconButton(
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        imageVector = IgIcons.Template,
                        contentDescription = "Preview",
                    )
                }
            )
            IgTextButton(
                onClick = { /*TODO*/ },
                text = { Text(text = "저장") }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
private fun PreviewEditPageScreen() {

    val thumbnail = Thumbnail(
        ImageBoxInfo(
            id = "image",
            boxData = BoxData(
                offsetRatioX = 0.2f,
                offsetRatioY = 0.2f,
                widthRatio = 0.5f,
                heightRatio = 0.3f
            )
        ),
        image = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.img),
        listOf(
            TextBoxInfo(
                id = "abc",
                text = "ABC",
                fontSizeRatio = 0.05f,
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.2f,
                    widthRatio = 0.5f,
                    heightRatio = 0.3f
                )
            ),
            TextBoxInfo(
                id = "def",
                text = "DEF",
                fontSizeRatio = 0.05f,
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.4f,
                    widthRatio = 0.5f,
                    heightRatio = 0.3f
                )
            ),
            TextBoxInfo(
                id = "ghi",
                text = "GHI",
                fontSizeRatio = 0.05f,
                boxData = BoxData(
                    offsetRatioX = 0.2f,
                    offsetRatioY = 0.6f,
                    widthRatio = 0.5f,
                    heightRatio = 0.3f
                )
            )
        )
    )

    IgTheme() {
        EditBookScreen(
            modifier = Modifier,
            thumbnail = thumbnail
        )
    }

}