package com.tntt.boxstate.datasource

import com.tntt.boxstate.model.BoxStateDto

interface RemoteBoxStateDataSource {
    fun createBoxStateDto(boxStateDto: BoxStateDto): String
    fun getBoxStateDto(boxId: String): BoxStateDto
    fun updateBoxStateDto(boxStateDto: BoxStateDto): Boolean
    fun deleteBoxStateDto(id: String): Boolean
}