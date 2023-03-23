package com.tntt.boxstate.datasource

import com.tntt.boxstate.model.BoxStateDto
import com.tntt.network.Firestore
import java.util.UUID

object RemoteBoxStateDataSourceImpl : RemoteBoxStateDataSource {

    val boxStateCollection by lazy { Firestore.firestore.collection("boxState") }

    override fun createBoxStateDto(boxStateDto: BoxStateDto): String {
        val boxStateId = UUID.randomUUID().toString()
        boxStateDto.id = boxStateId
        boxStateCollection
            .document(boxStateId)
            .set(boxStateDto)
        return boxStateId
    }

    override fun getBoxStateDto(boxId: String): BoxStateDto {
        lateinit var boxStateDto: BoxStateDto

        boxStateCollection
            .whereEqualTo("boxId", boxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw NullPointerException()

                val data = documentSnapshot.data
                val id = data?.get("id").toString()
                val offsetRatioX = data?.get("offsetRatioX").toString().toFloat()
                val offsetRatioY = data?.get("offsetRatioY").toString().toFloat()
                val widthRatio = data?.get("widthRatio").toString().toFloat()
                boxStateDto = BoxStateDto(id, boxId, offsetRatioX, offsetRatioY, widthRatio)
            }
        return boxStateDto
    }

    override fun updateBoxStateDto(boxStateDto: BoxStateDto): Boolean {
        var result: Boolean = true
        boxStateCollection
            .document(boxStateDto.id)
            .set(boxStateDto)
            .addOnFailureListener {
                result = false
            }
        return result
    }

    override fun deleteBoxStateDto(id: String): Boolean {
        var result: Boolean = true
        boxStateCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                result = false
            }
        return result
    }
}