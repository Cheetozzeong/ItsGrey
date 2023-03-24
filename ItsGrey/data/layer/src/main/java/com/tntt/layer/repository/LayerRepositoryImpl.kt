package com.tntt.layer.repository

import android.graphics.Bitmap
import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.imagebox.datasource.RemoteImageBoxDataSourceImpl
import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.layer.datasource.RemoteLayerDataSourceImpl
import com.tntt.layer.model.LayerDto
import com.tntt.model.LayerInfo
import com.tntt.network.retrofit.RetrofitNetwork
import com.tntt.repo.LayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LayerRepositoryImpl : LayerRepository {

    val layerDataSource: RemoteLayerDataSource by lazy { RemoteLayerDataSourceImpl }
    val imageBoxDataSource: RemoteImageBoxDataSource by lazy { RemoteImageBoxDataSourceImpl }

    override fun createLayerInfo(imageBoxId: String, layerInfo: LayerInfo): String {
        return layerDataSource.createLayerDto(LayerDto("", imageBoxId, layerInfo.order, layerInfo.bitmap))
    }

    override fun getLayerInfoList(imageBoxId: String): List<LayerInfo> {
        val layerInfoList = mutableListOf<LayerInfo>()

        val layerDtoList = layerDataSource.getLayerDtoList(imageBoxId)
        for (layerDto in layerDtoList) {
            layerInfoList.add(LayerInfo(layerDto.id, layerDto.order, layerDto.bitmap))
        }
        return layerInfoList
    }

    override fun updateLayerInfoList(imageBoxId: String, layerInfoList: List<LayerInfo>): Boolean {
        val layerDtoList = mutableListOf<LayerDto>()

        for (layerInfo in layerInfoList) {
            layerDtoList.add(LayerDto(layerInfo.id, imageBoxId, layerInfo.order, layerInfo.bitmap))
        }
        return layerDataSource.updateLayerDtoList(layerDtoList)
    }

    override fun deleteLayerInfoList(imageBoxId: String): Boolean {
        return layerDataSource.deleteLayerDtoList(imageBoxId)
    }

    override fun getSumLayer(pageId: String): Bitmap {
        TODO("Not yet implemented")
    }

    // 서버 통신 테스트 메서드
    fun createRoughSketch() {
        val apiService = RetrofitNetwork.getApiService("http://146.56.113.80:8000/")
        GlobalScope.launch(Dispatchers.IO) {
            val response = apiService.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val data = response.body()
                    print(data)
                }
            }
        }
    }
}