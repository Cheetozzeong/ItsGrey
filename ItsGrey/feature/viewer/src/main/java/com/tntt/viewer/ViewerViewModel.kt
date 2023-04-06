package com.tntt.viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editbook.model.Book
import com.tntt.editbook.model.Page
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor(
    private val editBookUseCase: EditBookUseCase,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
) : ViewModel() {

//    private val pageArgs: EditBookPageArgs = EditBookPageArgs(savedStateHandle, stringDecoder)
//
//    val pageId = pageArgs.pageId

    private val _bookTitle = MutableStateFlow("")
    val bookTitle: StateFlow<String> = _bookTitle

    private val _thumbnailOfPageData = MutableStateFlow(listOf<Page>())
    val thumbnailOfPageData: StateFlow<List<Page>> = _thumbnailOfPageData.asStateFlow()

    private val _isCoverExist = MutableStateFlow(false)
    val isCoverExist: StateFlow<Boolean> = _isCoverExist

    private val _selectedPage = MutableStateFlow<Int>(0)
    val selectedPage: StateFlow<Int> = _selectedPage

    init {
        getBook(bookId = "ff18f9d3-4353-4fa5-9d48-66d45003913d")
    }

    private fun getBook(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            editBookUseCase.getBook(
                bookId,
            ).collect() {
                _bookTitle.value = it.bookInfo.title
                _thumbnailOfPageData.value = it.pages
            }
        }
    }

    private fun createPage(bookId: String, pageInfo: PageInfo) {

    }

    private fun savePages(bookId: String, pages: List<Page>) {

    }

    private fun publishBook(book: Book, userId: String) {

    }
    fun saveBook(book: Book, userId: String, bookType: BookType = BookType.WORKING) {

    }

    fun deletePage(pageId: String) {
    }

    fun updatePageOrder(from: Int, to: Int) {
        val newList = _thumbnailOfPageData.value
        if (from < to) {
            for (i in from until to) {
                newList[i].pageInfo.order -= 1
            }
        } else if (from > to) {
            for (i in to + 1..from) {
                newList[i].pageInfo.order += 1
            }
        }
        if (isCoverExist.value) {
            newList[to].pageInfo.order = to
        } else {
            newList[to].pageInfo.order = to + 1
        }
        _thumbnailOfPageData.value = newList
    }
    fun updateSelectedPage(index: Int) {
        _selectedPage.value = index
    }
}
