package com.tntt.layer.datasource

import com.tntt.layer.model.LayerDto

interface RemoteLayerDataSource {
    fun createLayerDto(layerDto: LayerDto): String
    fun getLayerDtoList(imageBoxId: String): List<LayerDto>
    fun updateLayerDtoList(layerDtoList: List<LayerDto>): Boolean
    fun deleteLayerDtoList(imageBoxId: String): Boolean
}