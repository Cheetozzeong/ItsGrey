package com.tntt.imagebox.datasource

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson

import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.BoxData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteImageBoxDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : RemoteImageBoxDataSource {

    val imageBoxCollection by lazy { firestore.collection("imageBox") }

    override suspend fun createImageBoxDto(imageBoxDto: ImageBoxDto): Flow<String> = flow {
        imageBoxCollection
            .document(imageBoxDto.id)
            .set(imageBoxDto)
            .await()
        emit(imageBoxDto.id)
    }

    override suspend fun getImageBoxDtoList(pageId: String): Flow<List<ImageBoxDto>> = flow {
        Log.d("function test", "getImageBoxDtoList(${pageId})")
        var imageBoxDtoList = mutableListOf<ImageBoxDto>()
        val querySnapshot = imageBoxCollection
            .whereEqualTo("pageId", pageId)
            .get()
            .await()

        val documentSnapshot = querySnapshot.documents
        for (document in documentSnapshot) {
            val data = document.data
            Log.d("function test", "getImageBoxDtoList data = ${data}")
            val id: String = data?.get("id") as String
            val boxDataHashMap = data?.get("boxData") as HashMap<String, Float>
            val url: String = data?.get("url") as String
            val gson = Gson()
            val boxData =
                gson.fromJson(gson.toJson(boxDataHashMap), BoxData::class.java)
            val imageBoxDto = ImageBoxDto(id, pageId, boxData, url)
            Log.d("function test", "getImageBoxDtoList imageBoxDto = ${imageBoxDto}")
            imageBoxDtoList.add(imageBoxDto)
        }

        emit(imageBoxDtoList)
    }

    override suspend fun updateImageBoxDto(imageBoxDto: ImageBoxDto): Flow<Boolean> = flow {
        var result = true
        imageBoxCollection
            .document(imageBoxDto.id)
            .set(imageBoxDto)
            .addOnFailureListener { result = false }
            .await()
        emit(result)
    }

    override suspend fun updateImageBoxDtoList(imageBoxDtoList: List<ImageBoxDto>): Flow<Boolean> = flow {
        var result: Boolean = true
        Log.d("function test","updateImageBoxDtoList")
        for (imageBoxDto in imageBoxDtoList) {
            imageBoxCollection
                .document(imageBoxDto.id)
                .set(imageBoxDto)
                .addOnFailureListener {
                    result = false
                }
                .await()
        }
        emit(result)
    }

    override suspend fun deleteImageBoxDto(imageBoxId: String): Flow<Boolean> = flow {
        val storageRef = storage.reference
        val imageRef = storageRef.child("/images/${imageBoxId}")
        imageRef.delete()

        var result: Boolean = true
        imageBoxCollection
            .document(imageBoxId)
            .delete()
            .addOnFailureListener {
                result = false
            }
            .await()
        emit(result)
    }

    override suspend fun setImageUrl(imageBoxId: String, url: String): Flow<Boolean> = flow {
        var result = true
        imageBoxCollection
            .document(imageBoxId)
            .update("url", url)
            .addOnFailureListener { result = false }
            .await()
        emit(result)
    }
}