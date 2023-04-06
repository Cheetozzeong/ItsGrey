package com.tntt.feature.editpage

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTextButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.model.ImageBoxInfo
import com.tntt.model.TextBoxInfo
import com.tntt.ui.PageForEdit


@Composable
internal fun EditPageRoute(
    viewModel: EditPageViewModel = hiltViewModel(),
    onImageToDrawClick: (imageBoxId: String, imageUri: Uri?) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getImageBoxList()
    }

    val textBoxList by viewModel.textBoxList.collectAsStateWithLifecycle()
    val imageBoxList by viewModel.imageBox.collectAsStateWithLifecycle()
    val selectedBoxId by viewModel.selectedBoxId.collectAsStateWithLifecycle()

    EditPageScreen(
        textBoxList = textBoxList,
        imageBox = imageBoxList,
        selectedBoxId = selectedBoxId,
        onBackClick = viewModel::savePage,
        onImageToDrawClick = { id, uri ->
            if(uri != null) {
                viewModel.updateImageBox(id, uri)
            }
            onImageToDrawClick(id, uri)
        },
        onCreateTextBox = viewModel::createTextBox,
        onCreateImageBox = viewModel::createImageBox,
        updateTextBox = viewModel::updateTextBox,
        updateImageBox = viewModel::updateImageBox,
        onBoxSelected = viewModel::onBoxSelected,
        deleteBox = viewModel::deleteBox
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditPageScreen(
    textBoxList: List<TextBoxInfo>,
    imageBox: List<ImageBoxInfo>,
    selectedBoxId: String,
    onBackClick: () -> Unit,
    onImageToDrawClick: (imageBoxId: String, imageUri: Uri?) -> Unit,
    onCreateTextBox: () -> Unit,
    onCreateImageBox: () -> Unit,
    updateTextBox: (TextBoxInfo) -> Unit,
    updateImageBox: (ImageBoxInfo) -> Unit,
    onBoxSelected: (String) -> Unit,
    deleteBox: (String) -> Unit
) {
    Scaffold(
        topBar = {
            EditBookTopAppBar(
                onBackClick
            )
        },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                if(imageBox.isEmpty()) {
                    CreateImageBoxButton(onCreateImageBox = onCreateImageBox)
                }
                CreateTextBoxButton(onCreateTextBox = onCreateTextBox)
            }
            EditPageBox(
                textBoxList = textBoxList,
                imageBox = imageBox,
                onImageToDrawClick = onImageToDrawClick,
                selectedBoxId = selectedBoxId,
                updateTextBox = updateTextBox,
                updateImageBox = updateImageBox,
                onBoxSelected = onBoxSelected,
                deleteBox = deleteBox
            )
        }
    }
}

@Composable
fun EditPageBox(
    textBoxList: List<TextBoxInfo>,
    imageBox: List<ImageBoxInfo>,
    selectedBoxId: String,
    onImageToDrawClick: (imageBoxId: String, imageUri: Uri?) -> Unit,
    updateTextBox: (TextBoxInfo) -> Unit,
    updateImageBox: (ImageBoxInfo) -> Unit,
    onBoxSelected: (String) -> Unit,
    deleteBox: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .pointerInput(Unit) {
                detectTapGestures { onBoxSelected("") }
            },
        contentAlignment = Alignment.Center
    ) {
        PageForEdit(
            modifier = Modifier
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.background),
            textBoxList = textBoxList,
            imageBoxList = imageBox,
            selectedBoxId = selectedBoxId,
            updateTextBox = updateTextBox,
            updateImageBox = updateImageBox,
            onBoxSelected = onBoxSelected,
            deleteBox = deleteBox,
            imageBoxDialogComponent = listOf (
                {
                    ChangeImageButton(
                        imageUri = { uri ->
                            if(uri == null) return@ChangeImageButton
                            onImageToDrawClick(selectedBoxId, uri)
                        }
                    )
                },
                {
                    EditImageDrawingButton(
                        navToDrawing = {
                            onImageToDrawClick(selectedBoxId, null)
                        }
                    )
                }
            )
        )
    }
}

@Composable
private fun EditImageDrawingButton(navToDrawing: () -> Unit) {
    IgTextButton(
        onClick = { navToDrawing() },
        text = {Text(text = "수정")}
    )
}


@Composable
private fun ChangeImageButton(imageUri: (Uri?) -> Unit) {

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri(result.uriContent)
        } else {
            val exception = result.error
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        if(uri == null) return@rememberLauncherForActivityResult
        val cropOptions = CropImageContractOptions(uri, CropImageOptions())
        imageCropLauncher.launch(cropOptions)
    }

    IgTextButton(
        onClick = { imagePickerLauncher.launch("image/*") },
        text = {Text(text = "이미지 변경")}
    )
}

@Composable
private fun CreateImageBoxButton(
    onCreateImageBox: () -> Unit
) {
    IgIconButton(
        onClick = {
            onCreateImageBox()
        },
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
        actions = {}
    )
}
