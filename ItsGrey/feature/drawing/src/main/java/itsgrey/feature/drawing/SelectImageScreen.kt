package itsgrey.feature.drawing

import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Sample(
    viewModel: DrawingViewModel = hiltViewModel()
) {

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        val uri by viewModel::imageUri
        if(uri != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(LocalContext.current.getContentResolver(),
                Uri.parse(viewModel::imageUri.get())
            )
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "")
        }
        Text(text = viewModel::imageBoxId.get())
        Text(text = viewModel::imageUri.get().toString())
    }
}

