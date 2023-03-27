package com.tntt.page.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.network.Firestore
import com.tntt.page.model.PageDto
import java.util.UUID
import javax.inject.Inject

class RemotePageDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemotePageDataSource {

    val pageCollection by lazy { firestore.collection("page") }

    override fun createPageDto(pageDto: PageDto): String {
        val pageId = UUID.randomUUID().toString()
        pageDto.id = pageId
        pageCollection.document(pageId).set(pageDto)
        return pageId
    }

    override fun getPageDto(bookId: String, pageOrder: Int): PageDto {
        var pageDto: PageDto = PageDto("1", "1", 1)
        pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", pageOrder)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw  NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().documentSnapshot")

                val id = documentSnapshot.id
                val data = documentSnapshot.data
                val order = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")

                pageDto = PageDto(id, bookId, order)

            }
        return pageDto
    }

    override fun getFirstPageDto(bookId: String): PageDto {
        var pageDto: PageDto = PageDto("1", "1", 1)
        pageCollection
            .whereEqualTo("bookId", bookId)
            .orderBy("order")
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw  NullPointerException()

                val id = documentSnapshot.id
                val data = documentSnapshot.data
                val order = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")

                pageDto = PageDto(id, bookId, order)
            }
        return pageDto
    }

    override fun getPageDtoList(bookId: String): List<PageDto> {
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
            }
        return pageDtoList
    }

    override fun updatePageDto(pageDtoList: List<PageDto>): Boolean {
        var result: Boolean = true

        for (pageDto in pageDtoList) {
            pageCollection
                .document(pageDto.id)
                .set(pageDto)
                .addOnFailureListener { result = false }
        }
        return result
    }

    override fun hasCover(bookId: String): Boolean {
        var result = true
        pageCollection
            .whereEqualTo("bookId", bookId)
            .orderBy("order")
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw NullPointerException()
                val data = documentSnapshot.data
                result = (data?.get("order") as Int == 0)
            }
        return result
    }
}