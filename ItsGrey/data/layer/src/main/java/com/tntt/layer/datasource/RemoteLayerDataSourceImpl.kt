package com.tntt.layer.datasource

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tntt.layer.model.LayerDto
import com.tntt.network.Firestore
import com.tntt.network.retrofit.RetrofitNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class RemoteLayerDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteLayerDataSource {

    val layerCollection by lazy { firestore.collection("layer") }

    override suspend fun createLayerDto(layerDto: LayerDto): Flow<LayerDto> = flow {
        layerCollection
            .document(layerDto.id)
            .set(layerDto)
            .await()
        emit(layerDto)
    }

    override suspend fun getLayerDtoList(imageBoxId: String): Flow<List<LayerDto>> = flow {
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
            }.await()
        emit(layerDtoList)
    }

    override suspend fun updateLayerDtoList(layerDtoList: List<LayerDto>): Flow<Boolean> = flow {
        var result: Boolean = true
        for (layerDto in layerDtoList) {
            layerCollection
                .document(layerDto.id)
                .set(layerDto)
                .addOnSuccessListener { result = false }
                .await()
        }
        emit(result)
    }

    override suspend fun deleteLayerDtoList(imageBoxId: String): Flow<Boolean> = flow {
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
            }.await()
        emit(result)
    }

    override suspend fun getSumLayer(imageBoxId: String): Flow<Bitmap> = flow {
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
            }.await()

        val sumLayer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        sumLayer.eraseColor(Color.WHITE)

        val canvas = Canvas(sumLayer)
        for (bitmap in bitmapList) {
            canvas.drawBitmap(bitmap, 0f, 0f, null)
        }
        emit(sumLayer)
    }

    override suspend fun getSketchBitmap(bitmap: Bitmap): Flow<Bitmap> = flow {
        TODO("Not yet implemented")
    }

    override suspend fun retrofitTest(): Flow<String> = flow {
        val apiService = RetrofitNetwork.getApiService("http://146.56.113.80:8000")
        val response = apiService.getData()
        if(response.isSuccessful) {
            val data = response.body()
            Log.d("function test", "retrofitTest -> data = ${data}")
        }

    }
}