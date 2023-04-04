package com.tntt.itsgrey

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.tntt.domain.drawing.usecase.DrawingUseCase
import com.tntt.itsgrey.navigation.IgNavHost
import com.tntt.model.LayerInfo
import dagger.hilt.android.AndroidEntryPoint
import itsgrey.app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var drawingUseCase: DrawingUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch(Dispatchers.IO) {
            val bitmap =
                BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ironman)
            val layerList = mutableListOf<LayerInfo>()
            drawingUseCase.saveImage(bitmap, "ironman1.jpg").collect() { url ->
                Log.d("function test", "saveImage url = ${url}")
                drawingUseCase.getImage(url.toString()).collect() { bitmap ->
                    Log.d("function test", "getImage bitmap = ${bitmap}")
                    drawingUseCase.getSketch(bitmap).collect() { newBitmap ->
                        Log.d("function test", "getSketch newBitmap = ${newBitmap}")
                        drawingUseCase.saveImage(newBitmap, "ironman2.jpg").collect() { newUrl ->
                            Log.d("function test", "newUrl = ${newUrl}")

                        }
                    }
                }
            }
        }

        setContent {
            val navController = rememberNavController()
            IgNavHost(navController)
        }
    }

}