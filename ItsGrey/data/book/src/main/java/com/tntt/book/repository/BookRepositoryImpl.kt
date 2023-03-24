package com.tntt.book.repository

import android.util.Log
import com.tntt.book.datasource.RemoteBookDataSource
import com.tntt.model.BookType
import com.tntt.model.SortType
import com.tntt.model.BookInfo
import com.tntt.repo.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    init{
        Log.d("뭐 hilt test", "레포")
    }
    override fun getBookInfos(
        userId: String,
        sortType: SortType,
        startIndex: Long,
        bookType: BookType
    ): List<BookInfo> {
        val bookDtoList = remoteBookDataSource.getBookDtos(userId, sortType, startIndex, bookType)
        val bookList = mutableListOf<BookInfo>()
        for (bookDto in bookDtoList){
            bookList.add(BookInfo(bookDto.id, bookDto.title, bookDto.saveDate))
        }
        return bookList
    }

    override fun createBook(userId: String): String {
        return remoteBookDataSource.createBookDto(userId)
    }

    override fun deleteBook(bookIds: List<String>): Boolean {
        return remoteBookDataSource.deleteBook(bookIds)
    }
}