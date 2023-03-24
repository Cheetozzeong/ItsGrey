package com.tntt.di

import com.tntt.book.repository.BookRepositoryImpl
import com.tntt.drawing.repository.DrawingRepositoryImpl
import com.tntt.imagebox.repository.ImageBoxRepositoryImpl
import com.tntt.layer.repository.LayerRepositoryImpl
import com.tntt.page.repository.PageRepositoryImpl
import com.tntt.repo.*
import com.tntt.textbox.repository.TextBoxRepositoryImpl
import com.tntt.user.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsBookRepository(
        bookRepository: BookRepositoryImpl
    ): BookRepository

    @Binds
    fun bindsDrawingRepository(
        drawingRepository: DrawingRepositoryImpl
    ): DrawingRepository

    @Binds
    fun bindsImageBoxRepository(
        imageBoxRepository: ImageBoxRepositoryImpl
    ): ImageBoxRepository

    @Binds
    fun bindsLayerRepository(
        layerRepository: LayerRepositoryImpl
    ): LayerRepository

    @Binds
    fun bindsPageRepository(
        pageRepository: PageRepositoryImpl
    ): PageRepository

    @Binds
    fun bindsTextBoxRepository(
        textBoxRepository: TextBoxRepositoryImpl
    ): TextBoxRepository

    @Binds
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}