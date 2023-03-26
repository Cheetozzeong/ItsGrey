package com.tntt.hilttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.FirebaseApp
import com.tntt.home.usecase.BookUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class hilttest : AppCompatActivity() {

    @Inject
    lateinit var createBookUseCase: BookUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilttest)
        FirebaseApp.initializeApp(this)


        val button = findViewById<Button>(R.id.buttonForTest)
        button.setOnClickListener {
            createBookUseCase.testMethod()
        }
    }
}