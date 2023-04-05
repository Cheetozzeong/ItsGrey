package com.tntt.itsgrey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.tntt.designsystem.theme.IgTheme
import com.tntt.itsgrey.navigation.IgNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.tntt.domain.drawing.usecase.DrawingUseCase
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.editpage.usecase.EditPageUseCase
import com.tntt.home.usecase.HomeUseCase
import com.tntt.model.*
import itsgrey.app.R
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUserEmail = intent.getStringExtra("currentUserEmail")
        val currentUserName = intent.getStringExtra("currentUserName")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            IgTheme {
                IgNavHost(
                    navController = navController,
                    currentUserEmail = currentUserEmail!!,
                    currentUserName = currentUserName!!
                )
            }
        }
    }
}