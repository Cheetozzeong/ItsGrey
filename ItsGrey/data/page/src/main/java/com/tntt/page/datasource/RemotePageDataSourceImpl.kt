package com.tntt.page.datasource

import com.tntt.network.Firestore
import com.tntt.page.model.PageDto
import java.util.UUID

object RemotePageDataSourceImpl : RemotePageDataSource {

    val pageCollection by lazy { Firestore.firestore.collection("page") }

    override fun createPageDto(pageDto: PageDto): String {
        val pageId = UUID.randomUUID().toString()
        pageDto.id = pageId
        pageCollection.document(pageId).set(pageDto)
        return pageId
    }

    override fun getPageDto(bookId: String, pageOrder: Int): PageDto {
        lateinit var pageDto: PageDto
        pageCollection
            .whereEqualTo("bookId", bookId)
            .whereEqualTo("order", pageOrder)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw  NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().documentSnapshot")

                if (documentSnapshot != null) {
                    val id = documentSnapshot.id
                    val data = documentSnapshot.data
                    val order = data?.get("order").toString().toInt() ?: throw NullPointerException(":data:page - datasource/RemotePageDatasourceImpl.getPage().order")

                    pageDto = PageDto(id, bookId, order)
                }
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
}