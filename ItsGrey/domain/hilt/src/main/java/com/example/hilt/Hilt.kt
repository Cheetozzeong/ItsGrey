package com.example.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tntt.home.usecase.CreateBookUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Hilt : AppCompatActivity() {

    @Inject lateinit var userCase: CreateBookUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt)

        val createButton = findViewById<Button>(R.id.createButton)
        createButton.setOnClickListener { userCase.testMethod() }
    }
}