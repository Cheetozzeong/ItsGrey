package itsgrey.feature.drawing

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.model.DrawingInfo
import com.tntt.model.LayerInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.sketchbook.SketchbookController
import itsgrey.feature.drawing.navigation.imageBoxIdArgs
import itsgrey.feature.drawing.navigation.imageUriArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

    val imageBoxId: String = checkNotNull(savedStateHandle[imageBoxIdArgs])
    val imageUri: String? = savedStateHandle[imageUriArgs]

    private val _layerList: MutableStateFlow<List<LayerInfo>> = MutableStateFlow(emptyList())
    val layerList: StateFlow<List<LayerInfo>> = _layerList

    private val _drawingInfo: MutableStateFlow<DrawingInfo> = MutableStateFlow(DrawingInfo())
    val drawingInfo: StateFlow<DrawingInfo> = _drawingInfo

    private val _selectedTool: MutableStateFlow<DrawingToolLabel> = MutableStateFlow(DrawingToolLabel.Brush)
    val selectedTool: StateFlow<DrawingToolLabel> = _selectedTool

    private val _selectedLayer: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedLayer: StateFlow<Int> = _selectedLayer

    val aspectRatio: MutableStateFlow<Float> = MutableStateFlow(1f)

    init {

        // TODO: boxData 불러오기

        when(imageUri) {
            null -> {   // 기존 이미지 불러오기
                // TODO: 기존 layer 불러오기
                // TODO: drawing 불러오기
            }
            else -> {   // 새로운 이미지를 변환해서 불러오기
                // TODO: 이미지 선화로 변환해서 layer 불러오기
            }
        }

        // fake
        _layerList.value = listOf(
            LayerInfo(
                id = "first",
                order = 0,
                bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
            ),
            LayerInfo(
                id = "second",
                order = 1,
                bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
            )
        )
        aspectRatio.value = 2f / 3f
        _selectedLayer.value = 1
    }

    fun selectTool(selectedTool: DrawingToolLabel) {
        _selectedTool.value = selectedTool
    }

    fun updateBitmap(bitmap: Bitmap) {
        _layerList.value[selectedLayer.value].bitmap = bitmap
    }

}