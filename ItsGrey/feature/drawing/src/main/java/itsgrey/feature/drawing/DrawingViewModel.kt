package itsgrey.feature.drawing

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tntt.core.common.decoder.StringDecoder
import dagger.hilt.android.lifecycle.HiltViewModel
import itsgrey.feature.drawing.navigation.imageBoxIdArgs
import itsgrey.feature.drawing.navigation.imageUriArgs
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

    val imageBoxId: String = checkNotNull(savedStateHandle[imageBoxIdArgs])
    val imageUri: String? = savedStateHandle[imageUriArgs]

    init {
        when(imageUri) {
            null -> {

            }
            else -> {

            }
        }
    }
}