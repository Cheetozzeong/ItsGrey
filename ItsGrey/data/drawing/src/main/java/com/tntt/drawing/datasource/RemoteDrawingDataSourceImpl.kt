package com.tntt.drawing.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.drawing.model.DrawingDto
import com.tntt.network.Firestore
import java.util.UUID
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteDrawingDataSource {

    val drawingCollection by lazy { firestore.collection("drawing") }

    override fun createDrawingDto(drawingDto: DrawingDto): String {
        val id = UUID.randomUUID().toString()
        drawingDto.id = id
        drawingCollection
            .document(id)
            .set(drawingDto)
        return id
    }

    override fun getDrawingDto(imageBoxId: String): DrawingDto {

        lateinit var drawingDto: DrawingDto

        drawingCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents.firstOrNull() ?: throw NullPointerException()

                val data = documentSnapshot.data
                val id = data?.get("id") as String
                val penColor = data?.get("penColor") as String
                val recentColors = data?.get("recentColors") as List<String>
                drawingDto = DrawingDto(id, imageBoxId, penColor, recentColors)
            }
        return drawingDto
    }

    override fun updateDrawingDto(drawingDto: DrawingDto): Boolean {
        var result: Boolean = true
        drawingCollection
            .document(drawingDto.id)
            .set(drawingDto)
            .addOnFailureListener { result = false }
        return result
    }

    override fun deleteDrawingDto(id: String): Boolean {
        var result: Boolean = true
        drawingCollection
            .document(id)
            .delete()
            .addOnFailureListener { result = false }
        return result
    }
}