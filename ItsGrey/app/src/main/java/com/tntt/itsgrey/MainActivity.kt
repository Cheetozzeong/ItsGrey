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

        lifecycleScope.launch(Dispatchers.Main) {
            val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ironman)

            drawingUseCase.getSketch(bitmap).collect() { bitmap ->
                drawingUseCase.saveImage(bitmap).collect() { result ->
                    Log.d("function test", "result = ${result}")
                }
            }
        }

        setContent {
            val navController = rememberNavController()
            IgNavHost(navController)
        }
    }

}