package com.tntt.layer.datasource

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
        val bitmapList = mutableListOf<Bitmap>()
        var width = 100
        var height = 100

        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .orderBy("order")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val bitmap = document.data?.get("bitmap") as Bitmap
                    bitmapList.add(bitmap)
                    width = bitmap.width
                    height = bitmap.height
                }
            }

        val sumLayer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        sumLayer.eraseColor(Color.WHITE)

        val canvas = Canvas(sumLayer)
        for (bitmap in bitmapList) {
            canvas.drawBitmap(bitmap, 0f, 0f, null)
        }
        return sumLayer
    }
}