package com.tntt.home.usecase

import com.tntt.repo.BookRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class CreateBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
){
    fun testMethod(): String {
        print("test method")
        return "test method"
    }
}