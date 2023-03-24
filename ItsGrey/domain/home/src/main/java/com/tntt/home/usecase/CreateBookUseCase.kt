package com.tntt.home.usecase

import android.util.Log
import com.tntt.repo.BookRepository
import javax.inject.Inject

class CreateBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
){
    init{
        Log.d("뭐 hilt test", "CreateBookUseCase")
    }
    fun testMethod(): String {
        print("test method")
        return "test method"
    }
}