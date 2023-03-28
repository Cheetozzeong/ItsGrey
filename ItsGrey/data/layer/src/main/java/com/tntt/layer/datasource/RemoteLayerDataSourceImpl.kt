package com.tntt.layer.datasource

import android.graphics.Bitmap
import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.layer.model.LayerDto
import com.tntt.network.Firestore
import java.util.*
import javax.inject.Inject

class RemoteLayerDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteLayerDataSource {

    val layerCollection by lazy { firestore.collection("layer") }

    override fun createLayerDto(layerDto: LayerDto): String {
        val id = UUID.randomUUID().toString()
        layerDto.id = id
        layerCollection
            .document(id)
            .set(layerDto)
        return id
    }

    override fun getLayerDtoList(imageBoxId: String): List<LayerDto> {
        val layerDtoList = mutableListOf<LayerDto>()
        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val data = document.data
                    val id = data?.get("id") as String
                    val order = data?.get("order") as Int
                    val bitmap = data?.get("bitmap") as Bitmap
                    layerDtoList.add(LayerDto(id, imageBoxId, order, bitmap))
                }
            }
        return layerDtoList
    }

    override fun updateLayerDtoList(layerDtoList: List<LayerDto>): Boolean {
        var result: Boolean = true
        for (layerDto in layerDtoList) {
            layerCollection
                .document(layerDto.id)
                .set(layerDto)
                .addOnSuccessListener { result = false }
        }
        return result
    }

    override fun deleteLayerDtoList(imageBoxId: String): Boolean {
        var result: Boolean = true
        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    layerCollection
                        .document(document.id)
                        .delete()
                        .addOnFailureListener { result = false }
                }
            }
        return result
    }

    override fun getSumLayer(imageBoxId: String): Bitmap {
        TODO("Not yet implemented")
    }
}