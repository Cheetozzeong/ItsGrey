package com.tntt.home.usecase

import android.util.Log
import com.tntt.repo.BookRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateBookUseCase @Inject constructor(
    @ApplicationContext private val bookRepository: BookRepository,
){
    init{
        Log.d("뭐 hilt test", "CreateBookUseCase")
    }
    fun testMethod(): String {
        print("test method")
        return "test method"
    }
}