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
import com.tntt.editbook.model.Book
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.editpage.model.Page
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.home.usecase.HomeUseCase
import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.repository.ImageBoxRepositoryImpl
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
import java.util.*
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

    @Inject
    lateinit var imageBoxRepository: ImageBoxRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUserEmail = intent.getStringExtra("currentUserEmail")
        val currentUserName = intent.getStringExtra("currentUserName")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val userId = "하핫"
        val bookInfo = BookInfo("하핫북2", "하핫제목2", Date())
        val deleteBookIdList = listOf("하핫북6", "하핫북5")
        val pageInfo = PageInfo("2-하핫페이지1", 1)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test)
        val boxData = BoxData(0.25f, 0.65f, 0.3f, 0.3f)
        val imageBoxInfo = ImageBoxInfo("하핫이미지박스1", boxData, bitmap)
        val textBoxInfo = TextBoxInfo("하핫텍스트박스1", "하핫텍스트1", 0.3f, boxData)
        lifecycleScope.launch(Dispatchers.IO) {
            drawingUseCase.getLayerList(imageBoxInfo.id).collect() { layerList ->
                Log.d("haha", "layerList = ${layerList}")

            }
        }

        setContent {
            val navController = rememberNavController()
            IgNavHost(
                navController = navController,
                currentUserEmail = currentUserEmail!!,
                currentUserName = currentUserName!!
//                currentUserEmail = "",
//                currentUserName = "",
            )
        }
    }

}