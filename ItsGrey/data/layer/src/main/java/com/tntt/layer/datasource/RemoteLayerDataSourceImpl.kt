package com.tntt.layer.datasource

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tntt.layer.model.LayerDto
import com.tntt.network.retrofit.RetrofitNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.*
import javax.inject.Inject

class RemoteLayerDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : RemoteLayerDataSource {

    val layerCollection by lazy { firestore.collection("layer") }

    override suspend fun createLayerDto(layerDto: LayerDto): Flow<LayerDto> = flow {
        layerCollection
            .document(layerDto.id)
            .set(layerDto)
            .await()
        emit(layerDto)
    }

    override suspend fun getLayerDtoList(imageBoxId: String): Flow<List<LayerDto>> = flow {
        val layerDtoList = mutableListOf<LayerDto>()
        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val data = document.data
                    val id = data?.get("id") as String
                    val order = (data?.get("order") as Long).toInt()
                    val bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
                    layerDtoList.add(LayerDto(id, imageBoxId, order, bitmap))
                }
            }.await()
        emit(layerDtoList)
    }

    override suspend fun updateLayerDtoList(layerDtoList: List<LayerDto>): Flow<Boolean> = flow {
        var result: Boolean = true
        for (layerDto in layerDtoList) {
            layerCollection
                .document(layerDto.id)
                .set(layerDto)
                .addOnSuccessListener { result = false }
                .await()
        }
        emit(result)
    }

    override suspend fun deleteLayerDtoList(imageBoxId: String): Flow<Boolean> = flow {
        var result: Boolean = true
        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    layerCollection
                        .document(document.id)
                        .delete()
                        .addOnFailureListener { result = false }
                }
            }.await()
        emit(result)
    }

    override suspend fun getSumLayer(imageBoxId: String): Flow<Bitmap> = flow {
        val bitmapList = mutableListOf<Bitmap>()
        var width = 100
        var height = 100

        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .orderBy("order")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val bitmap = document.data?.get("bitmap") as Bitmap
                    bitmapList.add(bitmap)
                    width = bitmap.width
                    height = bitmap.height
                }
            }.await()

        val sumLayer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        sumLayer.eraseColor(Color.WHITE)

        val canvas = Canvas(sumLayer)
        for (bitmap in bitmapList) {
            canvas.drawBitmap(bitmap, 0f, 0f, null)
        }
        emit(sumLayer)
    }

    override suspend fun getSketchBitmap(bitmap: Bitmap): Flow<Bitmap> = flow {
        Log.d("function test", "getSketchBitmap(${bitmap})")
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        val apiService = RetrofitNetwork.getApiService()
        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), byteArray)
        val part = MultipartBody.Part.createFormData("file", "my_image.jpg", requestBody)
        val response = apiService.getSketch(part)
        Log.d("function test", "response = ${response}")

        val bmpByteArray = response.bytes()
        val options =BitmapFactory.Options().apply {
            // 이미지 크기 조정 등의 옵션
        }

        val resultBitmap = BitmapFactory.decodeByteArray(bmpByteArray, 0, bmpByteArray.size, options)

        emit(resultBitmap)
    }

    override suspend fun saveImage(bitmap: Bitmap): Flow<Uri?> = flow {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        val fileName = UUID.randomUUID().toString() + ".jpg"
        val storageRef = storage.reference
        val imageRef = storageRef.child("/images/${fileName}")
        val uploadTask = imageRef.putBytes(byteArray)

        var downloadUri: Uri? = null

        val urlTask = uploadTask.continueWithTask { task ->
            if(!task.isSuccessful)  {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                downloadUri = task.result
            }
        }.await()

        Log.d("fuction test", "downloadUri = ${downloadUri}")
        emit(downloadUri)

    }

    override suspend fun getImage(uri: Uri): Flow<Bitmap> = flow {
        var bitmap: Bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
        val storageRef = storage.getReference()
        emit(bitmap)
    }


}