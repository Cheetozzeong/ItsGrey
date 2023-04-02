package itsgrey.feature.drawing

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Sample(id: String, uri: Uri?) {

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = id)
        Text(text = uri.toString())
    }
}

