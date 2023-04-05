package com.tntt.imagebox.datasource

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.BoxData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteImageBoxDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteImageBoxDataSource {

    val imageBoxCollection by lazy { firestore.collection("imageBox") }

    override suspend fun createImageBoxDto(imageBoxDto: ImageBoxDto): Flow<String> = flow {
        imageBoxCollection
            .document(imageBoxDto.id)
            .set(imageBoxDto)
            .addOnSuccessListener { Log.d("function test", "success createImageBoxDto(${imageBoxDto})") }
            .await()
        emit(imageBoxDto.id)
    }

    override suspend fun getImageBoxDtoList(pageId: String): Flow<List<ImageBoxDto>> = flow {
        var imageBoxDtoList = mutableListOf<ImageBoxDto>()

        imageBoxCollection
            .whereEqualTo("pageId", pageId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull()

                if(documentSnapshot != null) {
                    val data = documentSnapshot.data
                    val id: String = data?.get("id") as String
                    val boxDataHashMap = data?.get("boxData") as HashMap<String, Float>
                    val url = data?.get("url") as String
                    val gson = Gson()
                    val boxData = gson.fromJson(gson.toJson(boxDataHashMap), BoxData::class.java)
                    imageBoxDtoList.add(ImageBoxDto(id, pageId, boxData, url))
                }
            }.await()
        emit(imageBoxDtoList)
    }

    override suspend fun updateImageBoxDto(imageBoxDto: ImageBoxDto): Flow<Boolean> = flow {
        var result = false
        imageBoxCollection
            .document(imageBoxDto.id)
            .set(imageBoxDto)
            .addOnSuccessListener { result = true }
            .await()
        emit(result)
    }

    override suspend fun updateImageBoxDtoList(imageBoxDtoList: List<ImageBoxDto>): Flow<Boolean> = flow {
        var result: Boolean = true
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

    override suspend fun deleteImageBoxDto(id: String): Flow<Boolean> = flow {
        var result: Boolean = true
        imageBoxCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                result = false
            }
            .await()
        emit(result)
    }
}