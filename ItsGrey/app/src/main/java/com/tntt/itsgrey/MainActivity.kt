package com.tntt.itsgrey

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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var drawingUseCase: DrawingUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        Log.d("================================","start")
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("function test", "packageName = ${packageName}")
            val uri: Uri = Uri.parse("android.resource://${packageName}/${R.drawable.ironman}")
            Log.d("fucntion test", "uri = ${uri}")
            drawingUseCase.saveImage(uri).collect() { result ->
                Log.d("function test", "activity storageTest uri = ${result}")
            }

//            drawingUseCase.getSketch(uri).collect() { bitmap ->
//                Log.d("function test", "success!")
//            }
        }

        setContent {
            val navController = rememberNavController()
            IgNavHost(navController)
        }
    }

}