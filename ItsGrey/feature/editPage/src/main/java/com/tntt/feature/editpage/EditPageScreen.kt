package com.tntt.feature.editpage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.editpage.model.Page
import com.tntt.model.BoxData
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.model.Thumbnail
import com.tntt.ui.PageForEdit
import kotlinx.coroutines.flow.StateFlow


@Composable
internal fun EditPageRoute(
    viewModel: EditPageViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit
) {
    val textBoxList by viewModel.textBoxList.collectAsStateWithLifecycle()
    val imageBox by viewModel.imageBox.collectAsStateWithLifecycle()

    EditPageScreen(
        textBoxList = textBoxList,
        imageBox = imageBox,
        onBackClick = onBackClick,
        onCreateTextBox = viewModel::createTextBox
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditPageScreen(
    textBoxList: List<TextBoxInfo>,
    imageBox: ImageBoxInfo,
    onBackClick: () -> Unit,
    onCreateTextBox: () -> Unit
) {
    Scaffold(
        topBar = {
            EditBookTopAppBar(
                onBackClick
            )
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Row {
                CreateTextBoxButton(
                    onCreateTextBox = onCreateTextBox
                )
            }
            EditPageBox(
                textBoxList = textBoxList,
                imageBox = imageBox,
            )
        }
    }
}

@Composable
fun EditPageBox(
    modifier: Modifier = Modifier,
    textBoxList: List<TextBoxInfo>,
    imageBox: ImageBoxInfo,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        PageForEdit(
            modifier = Modifier
                .width(300.dp)
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.background),
            textBoxList = textBoxList,
            imageBox = imageBox,
        )
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
private fun CreateTextBoxButton(
    onCreateTextBox: () -> Unit
) {
    IgIconButton(
        onClick = { onCreateTextBox() },
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
@Composable
fun EditBookTopAppBar(
    onBackClick: () -> Unit
) {
    IgTopAppBar(
        title = "",
        navigationIcon = IgIcons.NavigateBefore,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() },
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
        arrayListOf(
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
}
