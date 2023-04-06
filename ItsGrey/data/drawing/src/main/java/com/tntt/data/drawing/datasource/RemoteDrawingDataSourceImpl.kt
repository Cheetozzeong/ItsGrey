package com.tntt.data.drawing.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.data.drawing.model.DrawingDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteDrawingDataSource {

    val drawingCollection by lazy { firestore.collection("drawing") }

    override suspend fun createDrawingDto(drawingDto: DrawingDto): Flow<String> = flow {
        drawingCollection
            .document(drawingDto.id)
            .set(drawingDto)
            .await()
        emit(drawingDto.id)
    }

    override suspend fun getDrawingDto(imageBoxId: String): Flow<DrawingDto> = flow {

        val querySnapshot = drawingCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .await()

        val documentSnapshot = querySnapshot.documents.first()

        val data = documentSnapshot.data
        val id = data?.get("id") as String
        val penSizeList = data?.get("penSizeList") as List<Int>
        val eraserSizeList = data?.get("eraserSizeList") as List<Int>
        val penColor = data?.get("penColor") as String
        val recentColors = data?.get("recentColors") as List<String>
        val drawingDto = DrawingDto(id, imageBoxId, penSizeList, eraserSizeList, penColor, recentColors)

        emit(drawingDto)
    }

    override suspend fun updateDrawingDto(drawingDto: DrawingDto): Flow<Boolean> = flow {
        var result = true
        drawingCollection
            .document(drawingDto.id)
            .set(drawingDto)
            .addOnFailureListener { result = false }
            .await()
        emit(result)
    }

    override suspend fun deleteDrawingDto(imageBoxId: String): Flow<Boolean> = flow {
        var result: Boolean = true
        drawingCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    drawingCollection
                        .document(document.id)
                        .delete()
                        .addOnFailureListener { result = false }
                }
            }.await()
        emit(result)
    }
}