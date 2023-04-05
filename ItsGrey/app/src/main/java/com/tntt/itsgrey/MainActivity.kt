package com.tntt.itsgrey

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.tntt.domain.drawing.usecase.DrawingUseCase
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.home.usecase.HomeUseCase
import com.tntt.itsgrey.navigation.IgNavHost
import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.model.*
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
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeUseCase: HomeUseCase

    @Inject
    lateinit var editBookUseCase: EditBookUseCase

    @Inject
    lateinit var editPageUseCase: EditPageUseCase

    @Inject
    lateinit var drawingUseCase: DrawingUseCase

    @Inject
    lateinit var remoteLayerDataSource: RemoteLayerDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUserEmail = intent.getStringExtra("currentUserEmail")
        val currentUserName = intent.getStringExtra("currentUserName")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val upBoxData = BoxData(0.2f, 0.2f,0.4f, 0.4f)
        val downBoxData = BoxData(0.2f, 0.6f,0.4f, 0.4f)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_main)
        val imageBoxInfo = ImageBoxInfo("5", upBoxData, bitmap)
        val pageId = "24f3c3d8-21d3-40d9-9bd3-4a5b9bba8087"

        val imageBoxInfoList = mutableListOf<ImageBoxInfo>()
        val textBoxInfoList = mutableListOf<TextBoxInfo>()

        lifecycleScope.launch(Dispatchers.IO) {
//            homeUseCase.getBooks("1", SortType.TITLE, 0, BookType.WORKING).collect()
            editBookUseCase.getBook("0e39df61-40f1-42d0-8edc-0a7e63334f2f").collect() { book ->
                Log.d("function test", "book = ${book}")
            }
        }

        setContent {
            val navController = rememberNavController()
            IgNavHost(
                navController = navController,
                currentUserEmail = currentUserEmail!!,
                currentUserName = currentUserName!!
            )
        }
    }

}