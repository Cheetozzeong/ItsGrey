package com.tntt.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.tntt.core.common.decoder.StringDecoder
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
        getPublishedBookList(
            userId = userId,
            sortType = SortType.SAVE_DATE,
            startIndex = 0L
        )
        getWorkingBookList(
            userId = userId,
            sortType = SortType.SAVE_DATE,
            startIndex = 0L
        )
    }

//    savedStateHandle: SavedStateHandle,
//    stringDecoder: StringDecoder,

    fun createBook() {
        viewModelScope.launch {
            Log.d("GoogleSign","createBook")
            homeUseCase.createBook(
                userId,
                bookInfo = BookInfo(
                    id = UUID.randomUUID().toString(),
                    title = "새로운 책",
                    saveDate = Date()
                )
            ).collect()
        }
    }

    private fun getWorkingBookList(userId: String, sortType: SortType, startIndex: Long) {
        viewModelScope.launch {
            homeUseCase.getBooks(
                userId,
                sortType,
                startIndex,
                BookType.WORKING
            ).collect() { workingBookList -> _workingBookList.value = workingBookList }
        }
    }

    private fun getPublishedBookList(userId: String, sortType: SortType, startIndex: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            homeUseCase.getBooks(
                userId,
                sortType,
                startIndex,
                BookType.PUBLISHED
            ).collect() { publishedBookList -> _publishedBookList.value = publishedBookList }
        }
    }
}