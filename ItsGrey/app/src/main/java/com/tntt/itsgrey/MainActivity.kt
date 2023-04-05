package com.tntt.itsgrey

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.tntt.itsgrey.navigation.IgNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUserEmail = intent.getStringExtra("currentUserEmail")
        val currentUserName = intent.getStringExtra("currentUserName")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        lifecycleScope.launch(Dispatchers.IO) {
            
        }

        setContent {
            val navController = rememberNavController()
            IgNavHost(
                navController = navController,
                currentUserEmail = "",
//                currentUserEmail = currentUserEmail!!,
//                currentUserName = currentUserName!!
                currentUserName = ""
            )
        }
    }

}