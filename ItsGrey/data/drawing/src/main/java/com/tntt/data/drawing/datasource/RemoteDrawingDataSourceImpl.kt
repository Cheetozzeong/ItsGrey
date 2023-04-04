package com.tntt.data.drawing.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.data.drawing.model.DrawingDto
import com.tntt.network.Firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteDrawingDataSource {

    val drawingCollection by lazy { firestore.collection("drawing") }

    override suspend fun createDrawingDto(drawingDto: DrawingDto): Flow<String> = flow {
        val id = UUID.randomUUID().toString()
        drawingDto.id = id
        drawingCollection
            .document(id)
            .set(drawingDto)
        emit(id)
    }

    override suspend fun getDrawingDto(imageBoxId: String): Flow<DrawingDto> = flow {

        lateinit var drawingDto: DrawingDto

        drawingCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw NullPointerException()

                val data = documentSnapshot.data
                val id = data?.get("id") as String
                val penSizeList = data?.get("penSizeList") as List<Int>
                val eraserSizeList = data?.get("eraserSizeList") as List<Int>
                val penColor = data?.get("penColor") as String
                val recentColors = data?.get("recentColors") as List<String>
                drawingDto = DrawingDto(id, imageBoxId, penSizeList, eraserSizeList, penColor, recentColors)
            }
        emit(drawingDto)
    }

    override suspend fun updateDrawingDto(drawingDto: DrawingDto): Flow<Boolean> = flow {
        var result: Boolean = true
        drawingCollection
            .document(drawingDto.id)
            .set(drawingDto)
            .addOnFailureListener { result = false }
        emit(result)
    }

    override suspend fun deleteDrawingDto(id: String): Flow<Boolean> = flow {
        var result: Boolean = true
        drawingCollection
            .document(id)
            .delete()
            .addOnFailureListener { result = false }
        emit(result)
    }
}