package itsgrey.feature.drawing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.skydoves.orchestra.colorpicker.AlphaSlideBar
import com.skydoves.orchestra.colorpicker.BrightnessSlideBar
import com.skydoves.orchestra.colorpicker.ColorPicker
import kotlin.math.roundToInt

@Composable
fun ColorPicker(
    currentColor: Color,
    setSelectedColor: (Int) -> Unit,
    offset: Offset
) {
    Surface(modifier = Modifier
        .offset {
            IntOffset(
                offset.x.roundToInt() + 60,
                offset.y.roundToInt()
            )
        }
        .width(200.dp)
        .height(300.dp)
        .clip(RoundedCornerShape(10))
    ) {
        ColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp),
            onColorListener = { envelope, _ ->

                setSelectedColor(envelope.color)
            },
            initialColor = currentColor,
            children = { colorPickerView ->
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        AlphaSlideBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            colorPickerView = colorPickerView
                        )
                    }
                    Box(
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        BrightnessSlideBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            colorPickerView = colorPickerView
                        )
                    }
                }
            }
        )
    }
}