package com.tntt.imagebox.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.imagebox.model.ImageBoxDto
import com.tntt.model.BoxData
import com.tntt.model.BoxState
import com.tntt.network.Firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class RemoteImageBoxDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteImageBoxDataSource {

    val imageBoxCollection by lazy { firestore.collection("imageBox") }

    override suspend fun createImageBoxDto(imageBoxDto: ImageBoxDto): Flow<String> = flow {
        val imageBoxId = UUID.randomUUID().toString()
        imageBoxDto.id = imageBoxId
        imageBoxCollection
            .document(imageBoxId)
            .set(imageBoxDto)
        emit(imageBoxId)
    }

    override suspend fun getImageBoxDto(pageId: String): Flow<ImageBoxDto> = flow {
        var imageBoxDto: ImageBoxDto = ImageBoxDto("1", "1", BoxData(0.0f, 0.0f, 0.0f, 0.0f))

        imageBoxCollection
            .whereEqualTo("pageId", pageId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw NullPointerException(":data:imagebox - datasource/RemoteImageBoxDataSourceImpl.getImageBoxDto().documentSnapshot")

                val data = documentSnapshot.data
                val id: String = data?.get("id") as String
                val boxData: BoxData = data?.get("boxData") as BoxData

                imageBoxDto = ImageBoxDto(id, pageId, boxData)
            }
        emit(imageBoxDto)
    }

    override suspend fun updateImageBoxDto(imageBoxDto: ImageBoxDto): Flow<Boolean> = flow {
        var result: Boolean = true
        imageBoxCollection
            .document(imageBoxDto.id)
            .set(imageBoxDto)
            .addOnFailureListener {
                result = false
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
        emit(result)
    }
}