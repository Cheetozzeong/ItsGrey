package com.tntt.editbook

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tntt.core.common.decoder.StringDecoder
import com.tntt.editbook.model.Book
import com.tntt.editbook.model.Page
import com.tntt.editbook.usecase.EditBookUseCase
import com.tntt.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBookViewModel @Inject constructor(
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
    val thumbnailOfPageData: StateFlow<List<Page>> = _thumbnailOfPageData

    init {
        viewModelScope.launch {
            val book = flowOf(
                Book (
                    bookInfo = BookInfo(
                        id = "1",
                        title = "책 제목입니다요제목입니다요제목입니다요제목입니다요제목입니다요",
                        saveDate = Date()
                    ),
                    pages = listOf<Page>(
                        Page(
                            pageInfo = PageInfo(
                                id = "1",
                                order = 0
                            ),
                            thumbnail = Thumbnail(
                                imageBoxList = listOf(
                                    ImageBoxInfo(
                                        id = "1",
                                        boxData = BoxData(
                                            offsetRatioX = 0.0f,
                                            offsetRatioY = 0.0f,
                                            widthRatio = 1.0f,
                                            heightRatio = 1.0f,
                                        ),
                                        image = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888),
                                    )
                                ),
                                textBoxList = listOf(
                                    TextBoxInfo(
                                        id = "1",
                                        text = "123",
                                        fontSizeRatio = 80.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.2f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                    TextBoxInfo(
                                        id = "2",
                                        text = "456",
                                        fontSizeRatio = 10.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.4f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                )
                            )
                        ),
                        Page(
                            pageInfo = PageInfo(
                                id = "2",
                                order = 1
                            ),
                            thumbnail = Thumbnail(
                                imageBoxList = listOf(
                                    ImageBoxInfo(
                                        id = "1",
                                        boxData = BoxData(
                                            offsetRatioX = 0.0f,
                                            offsetRatioY = 0.0f,
                                            widthRatio = 1.0f,
                                            heightRatio = 1.0f,
                                        ),
                                        image = Bitmap.createBitmap(40, 40, Bitmap.Config.RGB_565),
                                    )
                                ),
                                textBoxList = listOf(
                                    TextBoxInfo(
                                        id = "1",
                                        text = "123",
                                        fontSizeRatio = 80.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.2f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                    TextBoxInfo(
                                        id = "2",
                                        text = "456",
                                        fontSizeRatio = 10.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.4f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                )
                            )
                        ),
                        Page(
                            pageInfo = PageInfo(
                                id = "3",
                                order = 2
                            ),
                            thumbnail = Thumbnail(
                                imageBoxList = listOf(
                                    ImageBoxInfo(
                                        id = "1",
                                        boxData = BoxData(
                                            offsetRatioX = 0.0f,
                                            offsetRatioY = 0.0f,
                                            widthRatio = 1.0f,
                                            heightRatio = 1.0f,
                                        ),
                                        image = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888),
                                    )
                                ),
                                textBoxList = listOf(
                                    TextBoxInfo(
                                        id = "1",
                                        text = "123",
                                        fontSizeRatio = 80.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.2f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                    TextBoxInfo(
                                        id = "2",
                                        text = "456",
                                        fontSizeRatio = 10.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.4f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                )
                            )
                        ),
                        Page(
                            pageInfo = PageInfo(
                                id = "5",
                                order = 3
                            ),
                            thumbnail = Thumbnail(
                                imageBoxList = listOf(
                                    ImageBoxInfo(
                                        id = "1",
                                        boxData = BoxData(
                                            offsetRatioX = 0.0f,
                                            offsetRatioY = 0.0f,
                                            widthRatio = 1.0f,
                                            heightRatio = 1.0f,
                                        ),
                                        image = Bitmap.createBitmap(40, 40, Bitmap.Config.RGB_565),
                                    )
                                ),
                                textBoxList = listOf(
                                    TextBoxInfo(
                                        id = "1",
                                        text = "123",
                                        fontSizeRatio = 80.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.2f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                    TextBoxInfo(
                                        id = "2",
                                        text = "456",
                                        fontSizeRatio = 10.0f,
                                        boxData = BoxData(
                                            offsetRatioX = 0.2f,
                                            offsetRatioY = 0.4f,
                                            widthRatio = 0.5f,
                                            heightRatio = 0.3f
                                        ),
                                    ),
                                )
                            )
                        ),
                    )
                )
            )
            book.collect() {
                _bookTitle.value = it.bookInfo.title
                _thumbnailOfPageData.value = it.pages
            }
        }
    }

//    private fun getBook(bookId: String) {
//        viewModelScope.launch {
//            editBookUseCase.getBook(
//                bookId,
//            ).collect() {
//                _bookTitle.value = it.bookInfo.title
//                _thumbnailOfPageData.value = it.pages
//            }
//        }
//    }
//
//    private fun createPage(bookId: String, pageInfo: PageInfo) {
//
//    }
//
//    private fun savePages(bookId: String, pages: List<Page>) {
//
//    }
//
//    private fun publishBook(book: Book, userId: String) {
//
//    }
//    fun saveBook(book: Book, userId: String, bookType: BookType = BookType.EDIT) {
//
//    }

    fun movePage(from: Int, to: Int) {
        _thumbnailOfPageData.value = _thumbnailOfPageData.value.toMutableList().apply {
            add(to, removeAt(from))
        }.toList()
        val currentList = thumbnailOfPageData.value.toMutableList()

        if (from < to) {
            for (i in from until to) {
                currentList[i].pageInfo.order -= 1
            }
        } else if (from > to) {
            for (i in to + 1..from) {
                currentList[i].pageInfo.order += 1
            }
        }
        currentList[to].pageInfo.order = to
        _thumbnailOfPageData.value = currentList

        updateSelectedPage(to)
    }

    private val _selectedPage = MutableStateFlow<Int>(0)
    val selectedPage: StateFlow<Int> = _selectedPage

    fun updateSelectedPage(index: Int) {
        _selectedPage.value = index
    }
}
