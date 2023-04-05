package com.tntt.page.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.page.model.PageDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemotePageDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemotePageDataSource {

    val pageCollection by lazy { firestore.collection("page") }

    override suspend fun createPageDto(pageDto: PageDto): Flow<String> = flow {
        pageCollection
            .document(pageDto.id)
            .set(pageDto)
            .await()
        emit(pageDto.id)
    }

    override suspend fun getPageDto(bookId: String, pageOrder: Int): Flow<PageDto> = flow {
        val querySnapshot = pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", pageOrder)
            .get()
            .await()

        val documentSnapshot = querySnapshot.documents.first() ?: throw  NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().documentSnapshot")

        val id = documentSnapshot.id
        val data = documentSnapshot.data
        val order = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")

        val pageDto = PageDto(id, bookId, order)
        emit(pageDto)
    }

    override suspend fun getFirstPageDto(bookId: String): Flow<PageDto?> = flow {
        var pageDto: PageDto? = null
        val querySnapshot = pageCollection
            .whereEqualTo("bookId", bookId)
            .orderBy("order")
            .limit(1)
            .get()
            .await()

        if(!querySnapshot.isEmpty) {
            val documentSnapshot = querySnapshot.documents.first()
            val id = documentSnapshot.id as String
            val data = documentSnapshot.data
            val order = (data?.get("order") as Long).toInt()

            pageDto = PageDto(id, bookId, order)
        }
        emit(pageDto)
    }

    override suspend fun getPageDtoList(bookId: String): Flow<List<PageDto>> = flow {
        val pageDtoList = mutableListOf<PageDto>()
        val querySnapshot = pageCollection
            .whereEqualTo("bookId", bookId)
            .orderBy("order")
            .get()
            .await()

        val documentSnapshot = querySnapshot.documents
        for (document in documentSnapshot) {
            val id = document.id
            val data = document.data
            val order: Int = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")
            pageDtoList.add(PageDto(id, bookId, order))
        }

        emit(pageDtoList)
    }

    override suspend fun updatePageDto(pageDtoList: List<PageDto>): Flow<Boolean> = flow {
        var result: Boolean = true

        for (pageDto in pageDtoList) {
            pageCollection
                .document(pageDto.id)
                .set(pageDto)
                .addOnFailureListener { result = false }
                .await()
        }
        emit(result)
    }

    override suspend fun hasCover(bookId: String): Flow<Boolean> = flow {
        var result = false
        val querySnapshot = pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", 0)
            .limit(1)
            .get()
            .await()

            if (!querySnapshot.isEmpty) result = true
        emit(result)
    }

    override suspend fun deletePageDto(pageId: String): Flow<Boolean> = flow {
        var result = false
        pageCollection
            .document(pageId)
            .delete()
            .addOnSuccessListener {
                result = true
            }.await()
        emit(result)
    }
}