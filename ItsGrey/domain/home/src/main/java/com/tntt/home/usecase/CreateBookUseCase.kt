package com.tntt.home.usecase

import com.tntt.repo.BookRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CreateBookUseCase {

    @Inject lateinit var bookRepository: BookRepository

    @Singleton
    @Provides
    fun testMethod(): String {
        print("test method")
        return "test method"
    }
}