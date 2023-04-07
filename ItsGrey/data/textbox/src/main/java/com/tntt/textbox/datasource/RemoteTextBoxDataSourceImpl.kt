package com.tntt.textbox.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.tntt.model.BoxData
import com.tntt.textbox.model.TextBoxDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteTextBoxDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteTextBoxDataSource {

    val textBoxCollection by lazy { firestore.collection("textBox") }

    override suspend fun createTextBoxDto(textBoxDto: TextBoxDto): Flow<String> = flow {
        textBoxCollection
            .document(textBoxDto.id)
            .set(textBoxDto)
            .await()
        emit(textBoxDto.id)
    }

    override suspend fun getTextBoxDtoList(pageId: String): Flow<List<TextBoxDto>> = flow {
        Log.d("function test", "getTextBoxDtoList(${pageId})")
        val textBoxDtoList = mutableListOf<TextBoxDto>()

        val querySnapshot = textBoxCollection
            .whereEqualTo("pageId", pageId)
            .get()
            .await()

        val documentSnapshot = querySnapshot.documents
        for (document in documentSnapshot) {
            val data = document.data

            val id = data?.get("id") as String
            val text = data?.get("text") as String
            val fontSizeRatio = (data?.get("fontSizeRatio") as Double).toFloat()
            val boxDataHashMap = data?.get("boxData") as HashMap<String, Float>
            val gson = Gson()
            val boxData = gson.fromJson(gson.toJson(boxDataHashMap), BoxData::class.java)
            textBoxDtoList.add(TextBoxDto(id, pageId, text, fontSizeRatio, boxData))
        }
        emit(textBoxDtoList)
    }

    override suspend fun updateTextBoxDtoList(textBoxDtoList: List<TextBoxDto>): Flow<Boolean> = flow {
        Log.d("function test", "updateTextBoxDtoList(${textBoxDtoList})")
        var result: Boolean = true
        Log.d("function test","updateTextBoxDtoList")
        for (textBoxDto in textBoxDtoList) {
            textBoxCollection
                .document(textBoxDto.id)
                .set(textBoxDto)
                .addOnFailureListener {
                    result = false
                }
                .await()
        }
        emit(result)
    }

    override suspend fun deleteTextBoxDto(id: String): Flow<Boolean> = flow {
        var result: Boolean = true

        textBoxCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                result = false
            }
            .await()
        emit(result)
    }
}