package com.tntt.data.drawing.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.data.drawing.model.DrawingDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteDrawingDataSource {

    val drawingCollection by lazy { firestore.collection("drawing") }

    override suspend fun createDrawingDto(drawingDto: DrawingDto) = callbackFlow<String> {
        drawingCollection
            .document(drawingDto.id)
            .set(drawingDto)
            .addOnSuccessListener {
                trySend(drawingDto.id)
            }
        awaitClose()
    }

    override suspend fun getDrawingDto(imageBoxId: String) = callbackFlow<DrawingDto> {

        drawingCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.first()

                val data = documentSnapshot.data
                val id = data?.get("id") as String
                val penSizeList = data?.get("penSizeList") as List<Int>
                val eraserSizeList = data?.get("eraserSizeList") as List<Int>
                val penColor = data?.get("penColor") as String
                val recentColors = data?.get("recentColors") as List<String>
                val drawingDto = DrawingDto(id, imageBoxId, penSizeList, eraserSizeList, penColor, recentColors)
                trySend(drawingDto)
            }
        awaitClose()
    }

    override suspend fun updateDrawingDto(drawingDto: DrawingDto) = callbackFlow<Boolean> {
        drawingCollection
            .document(drawingDto.id)
            .set(drawingDto)
            .addOnSuccessListener {
                trySend(true)
            }
        awaitClose()
    }

    override suspend fun deleteDrawingDto(imageBoxId: String) = callbackFlow<Boolean> {
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
            }
        trySend(result)
        awaitClose()
    }
}