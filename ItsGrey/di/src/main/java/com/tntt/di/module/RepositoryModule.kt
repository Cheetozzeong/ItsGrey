package com.tntt.di.module

import com.tntt.book.repository.BookRepositoryImpl
import com.tntt.repo.BookRepository
import com.tntt.repo.PageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsBookRepository(
        bookRepository: BookRepositoryImpl,
    ): BookRepository

}