package itsgrey.feature.drawing

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.domain.drawing.model.ImageBox
import com.tntt.domain.drawing.usecase.DrawingUseCase
import com.tntt.model.DrawingInfo
import com.tntt.model.LayerInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import itsgrey.feature.drawing.navigation.imageBoxIdArgs
import itsgrey.feature.drawing.navigation.imageUriArgs
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val drawingUseCase: DrawingUseCase,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

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

        when(imageUri) {
            null -> {   // 기존 이미지 불러오기
                viewModelScope.launch {
                    drawingUseCase.getLayerList(imageBoxId)
                        .collect() {
                            _layerList.value = it
                        }
                    drawingUseCase.getDrawing(imageBoxId)
                        .collect() {
                            _drawingInfo.value = it
                        }
                }
            }
            else -> {   // 새로운 이미지를 변환해서 불러오기
                viewModelScope.launch {
                    drawingUseCase.createLayerList(
                        imageBoxId,
                        MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(imageUri))
                    ).collect() {
                        _layerList.value = it
                    }
                }
            }
        }
        _selectedLayer.value = 0
    }

    fun selectTool(selectedTool: DrawingToolLabel) {
        _selectedTool.value = selectedTool
    }

    fun updateBitmap(bitmap: Bitmap) {
        _layerList.value[selectedLayer.value].bitmap = bitmap
    }

    fun save() {
        viewModelScope.launch {
            drawingUseCase.save(
                imageBox = ImageBox(
                    id = imageBoxId,
                    layerList = layerList.value,
                    drawing = drawingInfo.value,
                )
            ).collect()
        }
    }
}