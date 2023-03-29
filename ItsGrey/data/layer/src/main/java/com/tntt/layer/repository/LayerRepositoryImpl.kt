package com.tntt.layer.repository

import android.graphics.Bitmap
import com.tntt.imagebox.datasource.RemoteImageBoxDataSource
import com.tntt.layer.datasource.RemoteLayerDataSource
import com.tntt.layer.model.LayerDto
import com.tntt.model.LayerInfo
import com.tntt.repo.LayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LayerRepositoryImpl @Inject constructor(
    private val layerDataSource: RemoteLayerDataSource,
    private val imageBoxDataSource: RemoteImageBoxDataSource
): LayerRepository {

    override suspend fun createLayerInfo(imageBoxId: String, layerInfo: LayerInfo): Flow<LayerInfo> = flow {
        layerDataSource.createLayerDto(LayerDto("", imageBoxId, layerInfo.order, layerInfo.bitmap)).collect() { layerDtoId ->
            emit(layerInfo)
        }
    }

    override suspend fun getLayerInfoList(imageBoxId: String): Flow<List<LayerInfo>> = flow {
        val layerInfoList = mutableListOf<LayerInfo>()
        layerDataSource.getLayerDtoList(imageBoxId).collect() { layerDtoList ->
            for (layerDto in layerDtoList) {
                layerInfoList.add(LayerInfo(layerDto.id, layerDto.order, layerDto.bitmap))
            }
            emit(layerInfoList)
        }
    }

    override suspend fun updateLayerInfoList(imageBoxId: String, layerInfoList: List<LayerInfo>): Flow<Boolean> = flow {
        val layerDtoList = mutableListOf<LayerDto>()

        for (layerInfo in layerInfoList) {
            layerDtoList.add(LayerDto(layerInfo.id, imageBoxId, layerInfo.order, layerInfo.bitmap))
        }
        layerDataSource.updateLayerDtoList(layerDtoList).collect() { result ->
            emit(result)
        }
    }

    override suspend fun deleteLayerInfoList(imageBoxId: String): Flow<Boolean> = flow {
        layerDataSource.deleteLayerDtoList(imageBoxId).collect() { result ->
            emit(result)
        }
    }

    override suspend fun getSketchBitmap(bitmap: Bitmap): Flow<Bitmap> = flow {
        TODO("이미지 전달 후 밑그림 가져오기")
    }

    override suspend fun retrofitTest(): Flow<String> = flow {
        layerDataSource.retrofitTest().collect() { result ->
            emit(result)
        }
    }


    // 서버 통신 테스트 메서드
//    suspend fun createRoughSketch() {
//        val apiService = RetrofitNetwork.getApiService("http://146.56.113.80:8000/")
//        GlobalScope.launch(Dispatchers.IO) {
//            val response = apiService.getData()
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    print(data)
//                }
//            }
//        }
//    }
}