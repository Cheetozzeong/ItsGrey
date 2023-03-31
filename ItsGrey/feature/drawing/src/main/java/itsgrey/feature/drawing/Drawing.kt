package itsgrey.feature.drawing

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.orchestra.colorpicker.AlphaSlideBar
import com.skydoves.orchestra.colorpicker.BrightnessSlideBar
import com.skydoves.orchestra.colorpicker.ColorPicker
import com.tntt.designsystem.theme.IgTheme

//@Preview
@Composable
fun colorPicker(
    setSelectedColor: (String) -> Unit
) {
    Box(modifier = Modifier.width(400.dp).height(350.dp).border(3.dp,Color.White, RoundedCornerShape(10))) {
        ColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 10.dp),
            onColorListener = { envelope, _ ->
                setSelectedColor(envelope.hexCode)
            },
            initialColor = Color.Black,
            children = { colorPickerView ->
                Column(modifier = Modifier.padding(top = 32.dp)) {
                    Box(modifier = Modifier.padding(vertical = 6.dp)) {
                        AlphaSlideBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            colorPickerView = colorPickerView
                        )
                    }
                    Box(modifier = Modifier.padding(vertical = 6.dp)) {
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