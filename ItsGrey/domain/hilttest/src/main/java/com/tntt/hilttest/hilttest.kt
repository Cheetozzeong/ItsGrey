package com.tntt.hilttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tntt.home.usecase.CreateBookUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class hilttest : AppCompatActivity() {

    @Inject
    lateinit var createBookUseCase: CreateBookUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilttest)



        val button = findViewById<Button>(R.id.buttonForTest)
        button.setOnClickListener {
            createBookUseCase.testMethod()
        }
    }
}