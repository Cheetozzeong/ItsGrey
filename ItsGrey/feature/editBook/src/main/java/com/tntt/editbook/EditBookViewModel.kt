package com.tntt.editbook

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.editbook.model.Book
import com.tntt.editbook.model.Page
import com.tntt.editbook.Navigation.bookIdArg
import com.tntt.editbook.Navigation.userIdArg
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.*
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject

@HiltViewModel
class EditBookViewModel @Inject constructor(
    private val editBookUseCase: EditBookUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val bookId: String = checkNotNull(savedStateHandle[bookIdArg])
    private val userId: String = checkNotNull(savedStateHandle[userIdArg])

    private val _bookTitle = MutableStateFlow("")
    val bookTitle: StateFlow<String> = _bookTitle

    private val _thumbnailOfPageData = MutableStateFlow(listOf<Page>())
    val thumbnailOfPageData: StateFlow<List<Page>> = _thumbnailOfPageData.asStateFlow()

    private val _isCoverExist = MutableStateFlow(false)
    val isCoverExist: StateFlow<Boolean> = _isCoverExist

    private val _selectedPage = MutableStateFlow(1)
    val selectedPage: StateFlow<Int> = _selectedPage

    init {
        getBook()
    }

    fun getBook() {
        viewModelScope.launch(Dispatchers.IO) {
            editBookUseCase.getBook(
                bookId = bookId,
            ).collect() {
                this.launch(Dispatchers.Main) {
                    _bookTitle.value = it.bookInfo.title
                    _thumbnailOfPageData.value = it.pages
                    _isCoverExist.value =
                        if (_thumbnailOfPageData.value.isEmpty()) {
                            false
                        } else {
                            _thumbnailOfPageData.value[0].pageInfo.order == 0
                        }
                    updatePageOrder()
                }
            }
        }
    }

    fun createPage(isCover: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            editBookUseCase.createPage(
                bookId,
                pageInfo = PageInfo(
                    id = UUID.randomUUID().toString(),
                    order = if(isCover)  0
                            else if(isCoverExist.value) thumbnailOfPageData.value.size
                            else thumbnailOfPageData.value.size + 1
                )
            ).collect() {
                val newList = _thumbnailOfPageData.value.toMutableList()
                if(isCover) {
                    newList.add(0, it)
                    _isCoverExist.value = true
                }else {
                    newList.add(it)
                }
                this.launch(Dispatchers.Main) {
                    _thumbnailOfPageData.value = newList
                }
            }
        }
    }

    fun saveBook() {
        viewModelScope.launch(Dispatchers.IO) {
            editBookUseCase.saveBook(
                book = Book(
                    bookInfo = BookInfo(
                        id = bookId,
                        title = bookTitle.value,
                        saveDate = Date(),
                    ),
                    pages = thumbnailOfPageData.value
                ),
                userId = userId,
                bookType = BookType.WORKING
            ).collect()
        }
    }

    fun publishBook() {
        viewModelScope.launch(Dispatchers.IO) {
            editBookUseCase.publishBook(
                book = Book(
                    bookInfo = BookInfo(
                        id = bookIdArg,
                        title = bookTitle.value,
                        saveDate = Date(),
                    ),
                    pages = thumbnailOfPageData.value
                ),
                userId = userId
            ).collect()
        }
    }

    fun deletePage(pageId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            editBookUseCase.deletePage(
                pageId = pageId
            ).collect() {
                if (it) {
                    this.launch(Dispatchers.Main) {
                        getBook()
                    }
                }
            }
        }
    }

    fun movePage(from: Int, to: Int) {
        _thumbnailOfPageData.value = _thumbnailOfPageData.value.toMutableList().apply {
            add(to, removeAt(from))
        }.toList()

        updatePageOrder()
        updateSelectedPage(thumbnailOfPageData.value[to].pageInfo.order)
        saveBook()
    }

    fun isPageDragEnabled(draggedOver: ItemPosition, dragging: ItemPosition): Boolean {
        val draggedOrder =
            if (draggedOver.index >= _thumbnailOfPageData.value.size - 1) {
                null
            } else {
                thumbnailOfPageData.value[draggedOver.index].pageInfo.order
            }
        val draggingOrder = thumbnailOfPageData.value[dragging.index].pageInfo.order
        val isDraggingFirst = draggedOrder == 0
        val isDraggedFirst = draggingOrder == 0

        return !(isDraggingFirst || isDraggedFirst)
    }

    fun updatePageOrder() {
        thumbnailOfPageData.value.forEachIndexed { index, page ->
            if(isCoverExist.value) {
                page.pageInfo.order = index
            }else {
                page.pageInfo.order = index + 1
            }
        }
    }

    fun updateSelectedPage(index: Int) {
        _selectedPage.value = index
    }

    fun titleChange(title: String) {
        _bookTitle.value = title
    }
}