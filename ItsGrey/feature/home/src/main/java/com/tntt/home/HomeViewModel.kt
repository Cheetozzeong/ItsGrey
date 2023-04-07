package com.tntt.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tntt.home.model.Book
import com.tntt.home.navigation.userIdArg
import com.tntt.home.navigation.userNameArg
import com.tntt.home.usecase.HomeUseCase
import com.tntt.model.BookInfo
import com.tntt.model.BookType
import com.tntt.model.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val userId: String = checkNotNull(savedStateHandle[userIdArg])
    val userName: String = checkNotNull(savedStateHandle[userNameArg])

    private val _workingBookList = MutableStateFlow(listOf<Book>())
    val workingBookList: StateFlow<List<Book>> = _workingBookList

    private val _publishedBookList = MutableStateFlow(listOf<Book>())
    val publishedBookList: StateFlow<List<Book>> = _publishedBookList

    init {
        getPublishedBookList()
        getWorkingBookList()
    }

//    savedStateHandle: SavedStateHandle,
//    stringDecoder: StringDecoder,

    fun createBook() {
        CoroutineScope(Dispatchers.IO).launch {
            homeUseCase.createBook(
                userId,
                bookInfo = BookInfo(
                    id = UUID.randomUUID().toString(),
                    title = "새로운 책",
                    saveDate = Date()
                )
            ).collect()
            homeUseCase.getBooks(userId, SortType.SAVE_DATE, 0, BookType.WORKING).collect() { workingBookList ->
                this.launch(Dispatchers.Main) {
                    _workingBookList.value = workingBookList
                }
            }
        }
    }

    fun getWorkingBookList() {
        CoroutineScope(Dispatchers.IO).launch {
            homeUseCase.getBooks(
                userId,
                SortType.SAVE_DATE,
                0,
                BookType.WORKING
            ).collect() { workingBookList -> _workingBookList.value = workingBookList }
        }
    }

    fun getPublishedBookList() {
        CoroutineScope(Dispatchers.IO).launch {
            homeUseCase.getBooks(
                userId,
                SortType.SAVE_DATE,
                0,
                BookType.PUBLISHED
            ).collect() { publishedBookList -> _publishedBookList.value = publishedBookList }
        }
    }
}