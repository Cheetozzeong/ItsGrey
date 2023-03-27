package com.tntt.repo

import android.graphics.Bitmap
import com.tntt.model.LayerInfo

interface LayerRepository {
    fun createLayerInfo(imageBoxId: String, layerInfo: LayerInfo): String
    fun getLayerInfoList(imageBoxId: String): List<LayerInfo>
    fun updateLayerInfoList(imageBoxId: String, layerInfoList: List<LayerInfo>): Boolean
    fun deleteLayerInfoList(imageBoxId: String): Boolean
}