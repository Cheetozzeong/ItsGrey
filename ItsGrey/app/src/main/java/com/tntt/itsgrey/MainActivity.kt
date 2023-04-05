package com.tntt.itsgrey

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.tntt.designsystem.theme.IgTheme
import com.tntt.itsgrey.navigation.IgNavHost
import dagger.hilt.android.AndroidEntryPoint

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