package com.tntt.itsgrey

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.tntt.designsystem.theme.IgTheme
import com.tntt.home.usecase.HomeUseCase
import com.tntt.itsgrey.navigation.IgNavHost
import com.tntt.model.BookType
import com.tntt.model.SortType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeUseCase: HomeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.getBooks("1", SortType.TITLE, 0L,BookType.PUBLISHED).collect() { books ->
                Log.d("function test", "books = ${books}")
            }
        }

        setContent {
            IgTheme {
                val navController = rememberNavController()
                IgNavHost(navController)
            }
        }
    }
}