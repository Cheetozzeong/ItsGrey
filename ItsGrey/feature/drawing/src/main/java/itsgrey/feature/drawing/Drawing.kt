package itsgrey.feature.drawing

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.orchestra.colorpicker.AlphaSlideBar
import com.skydoves.orchestra.colorpicker.BrightnessSlideBar
import com.skydoves.orchestra.colorpicker.ColorPicker
import com.tntt.designsystem.component.IgIconButton
import com.tntt.designsystem.component.IgTopAppBar
import com.tntt.designsystem.icon.IgIcons
import com.tntt.designsystem.theme.IgTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Composable
fun drawingScreen(){
    IgTheme {
        val colorBackground = MaterialTheme.colorScheme.surface
        val borderColor = MaterialTheme.colorScheme.onPrimary

        Scaffold(
            modifier = Modifier,
            topBar = { IgTopAppBar(
                modifier = Modifier.padding(end = 25.dp),
                title = "",
                navigationIcon = IgIcons.NavigateBefore,
                navigationIconContentDescription = "Before",
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = IgIcons.Undo,
                            contentDescription = "Undo",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = IgIcons.Redo,
                            contentDescription = "Redo",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = "저장",
                        modifier = Modifier.clickable(
                            onClick = {/*TODO*/}
                        )
                    )
                }
            ) }
        ) { padding ->
            Row(
                Modifier
                    .padding(padding)
                    .fillMaxHeight()
                    .background(color = Color.Red)
            ) {
                // SideToolBox
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.surface)
                ){
                    Column(
                        Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
                    ) {
                        IgIconButton(
                            modifier = Modifier,
                            onClick = {/*TODO: 붓으로 변경*/},
                            icon = {
                                Icon(
                                    imageVector = IgIcons.Brush,
                                    contentDescription = "brushButton"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.padding(vertical = 10.dp))

                        IgIconButton(
                            modifier = Modifier,
                            onClick = { /*TODO: 지우개로 변경*/ },
                            icon = {
                                Icon(
                                    painter = painterResource(id = IgIcons.Eraser),
                                    contentDescription = "EraserButton"
                                )
                            }
                        )

                        Spacer(modifier = Modifier.padding(vertical = 10.dp))


                    }
                }

                // DrawingMainBox
                Box(modifier = Modifier
                    .weight(15f)
                    .background(color = MaterialTheme.colorScheme.inversePrimary)
                    .fillMaxHeight()
                ){

                }
            }
        }
    }
}

@Composable
fun colorPicker(
    setSelectedColor: (String) -> Unit
) {
    Box(modifier = Modifier
        .width(400.dp)
        .height(350.dp)
        .border(3.dp, Color.White, RoundedCornerShape(10))) {
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

@Composable
fun PreviewColorPicker(){
    colorPicker{ selectedColor ->
        Log.d("selectedColor", selectedColor)
    }
}