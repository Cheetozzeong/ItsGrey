package com.tntt.page.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.network.Firestore
import com.tntt.page.model.PageDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class RemotePageDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemotePageDataSource {

    val pageCollection by lazy { firestore.collection("page") }

    override suspend fun createPageDto(pageDto: PageDto): Flow<PageDto> = flow {
        Log.d("function test", "createPageDto(${pageDto})")
        pageCollection
            .document(pageDto.id)
            .set(pageDto)
            .addOnSuccessListener { Log.d("function test", "success createPageDto(${pageDto})") }
            .await()
        emit(pageDto)
    }

    override suspend fun getPageDto(bookId: String, pageOrder: Int): Flow<PageDto> = flow {
        Log.d("function test", "getPageDto(${bookId}, ${pageOrder})")
        var pageDto: PageDto = PageDto("1", "1", 1)
        println("getPageDto(${bookId}, ${pageOrder})")
        pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", pageOrder)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.first() ?: throw  NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().documentSnapshot")

                val id = documentSnapshot.id
                val data = documentSnapshot.data
                val order = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")

                pageDto = PageDto(id, bookId, order)

            }.await()
        emit(pageDto)
    }

    override suspend fun getCoverPageDto(bookId: String): Flow<PageDto?> = flow {
        Log.d("function test", "getFirstPageDto(${bookId})")
        var pageDto: PageDto? = null
        pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", 0)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if(!querySnapshot.isEmpty) {
                    val documentSnapshot = querySnapshot.documents.first()
                    val id = documentSnapshot.id as String
                    val data = documentSnapshot.data
                    val order = data?.get("order") as Int

                    pageDto = PageDto(id, bookId, order)
                }
            }.await()
        emit(pageDto)
    }

    override suspend fun getPageDtoList(bookId: String): Flow<List<PageDto>> = flow {
        Log.d("function test", "getPageDtoList(${bookId})")
        val pageDtoList = mutableListOf<PageDto>()
        pageCollection
            .whereEqualTo("bookId", bookId)
            .orderBy("order")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val id = document.id
                    val data = document.data
                    val order: Int = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")

                    pageDtoList.add(PageDto(id, bookId, order))
                }
            }.await()
        emit(pageDtoList)
    }

    override suspend fun updatePageDto(pageDtoList: List<PageDto>): Flow<Boolean> = flow {
        Log.d("function test", "updatePageDto(${pageDtoList}")
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
        Log.d("function test", "hasCover(${bookId})")
        var result = false
        pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", 0)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) result = true
            }
            .await()
        emit(result)
    }
}