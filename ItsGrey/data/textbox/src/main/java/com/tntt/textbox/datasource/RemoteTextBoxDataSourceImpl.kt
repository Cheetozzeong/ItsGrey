package com.tntt.textbox.datasource

import com.tntt.model.BoxState
import com.tntt.network.Firestore
import com.tntt.textbox.model.TextBoxDto
import java.util.UUID

object RemoteTextBoxDataSourceImpl : RemoteTextBoxDataSource {

    val textBoxCollection by lazy { Firestore.firestore.collection("textBox") }

    override fun createTextBoxDto(textBoxDto: TextBoxDto): String {
        val id = UUID.randomUUID().toString()
        textBoxDto.id = id
        textBoxCollection
            .document(id)
            .set(textBoxDto)
        return id
    }

    override fun getTextBoxDtoList(pageId: String): List<TextBoxDto> {
        val textBoxDtoList = mutableListOf<TextBoxDto>()

        textBoxCollection
            .whereEqualTo("pageId", pageId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val data = document.data

                    val id = data?.get("id") as String
                    val text = data?.get("text") as String
                    val fontSizeRatio = data?.get("fontSizeRatio") as Float
                    val boxState = data?.get("boxState") as BoxState
                    textBoxDtoList.add(TextBoxDto(id, pageId, text, fontSizeRatio, boxState))
                }
            }
        return textBoxDtoList
    }

    override fun updateTextBoxDtoList(textBoxDtoList: List<TextBoxDto>): Boolean {
        var result: Boolean = true

        for (textBoxDto in textBoxDtoList) {
            textBoxCollection
                .document(textBoxDto.id)
                .set(textBoxDto)
                .addOnFailureListener {
                    result = false
                }
        }
        return result
    }

    override fun deleteTextBoxDto(id: String): Boolean {
        var result: Boolean = true

        textBoxCollection
            .document(id)
            .delete()
            .addOnFailureListener {
                result = false
            }
        return result
    }
}