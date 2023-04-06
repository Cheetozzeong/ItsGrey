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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class RemoteLayerDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : RemoteLayerDataSource {

    val layerCollection by lazy { firestore.collection("layer") }

    override suspend fun createLayerDtoList(layerDtoList: List<LayerDto>): Flow<List<LayerDto>> = flow {
        for (layerDto in layerDtoList) {
            layerCollection
                .document(layerDto.id)
                .set(layerDto)
                .await()
        }
        emit(layerDtoList)
    }

    override suspend fun getLayerDtoList(imageBoxId: String): Flow<List<LayerDto>> = flow {
        val layerDtoList = mutableListOf<LayerDto>()
        val querySnapshot = layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .orderBy("order")
            .get()
            .await()

        val documentSnapshot = querySnapshot.documents
        for (document in documentSnapshot) {
            val data = document.data
            val id = data?.get("id") as String
            val order = (data?.get("order") as Long).toString().toInt()
            val url = data?.get("url") as String
            layerDtoList.add(LayerDto(id, imageBoxId, order, url))
        }
        emit(layerDtoList)
    }

    override suspend fun updateLayerDtoList(layerDtoList: List<LayerDto>): Flow<Boolean> = flow {
        var result = true
        for (layerDto in layerDtoList) {
            layerCollection
                .document(layerDto.id)
                .set(layerDto)
                .addOnFailureListener { result = false }
                .await()
        }
        emit(result)
    }

    override suspend fun deleteLayerDtoList(imageBoxId: String): Flow<Boolean> = flow {
        val storageRef = storage.reference

        var result: Boolean = true
        layerCollection
            .whereEqualTo("imageBoxId", imageBoxId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentSnapshot = querySnapshot.documents
                for (document in documentSnapshot) {
                    val imageRef = storageRef.child("/images/${document.id}")
                    imageRef.delete()

                    layerCollection
                        .document(document.id)
                        .delete()
                        .addOnFailureListener { result = false }
                }
            }.await()
        emit(result)
    }



    override suspend fun getSketchBitmap(bitmap: Bitmap): Flow<Bitmap> = flow {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val apiService = RetrofitNetwork.getApiService()
        val requestBody = RequestBody.create(MediaType.parse("image/png"), byteArray)
        val part = MultipartBody.Part.createFormData("file", "my_image.png", requestBody)
        val response = apiService.getSketch(part)

        val bmpByteArray = response.bytes()
        val options =BitmapFactory.Options().apply {
            // 이미지 크기 조정 등의 옵션
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }

        val sourceBitmap = BitmapFactory.decodeByteArray(bmpByteArray, 0, bmpByteArray.size, options)
        val resultBitmap = Bitmap.createBitmap(sourceBitmap.width, sourceBitmap.height, Bitmap.Config.ARGB_8888)

        for (x in 0 until resultBitmap.width) {
            for (y in 0 until resultBitmap.height) {
                val pixel = sourceBitmap.getPixel(x, y)
                if(pixel == Color.BLACK) {
                    resultBitmap.setPixel(x, y, Color.BLACK)
                }
            }
        }
        emit(resultBitmap)
    }

    override suspend fun saveImage(bitmap: Bitmap, url: String): Flow<Uri?> = flow {
        Log.d("function test", "saveImage(${bitmap}, ${url})")
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val storageRef = storage.reference
        val imageRef = storageRef.child("/images/${url}")
        val uploadTask = imageRef.putBytes(byteArray)

        val downloadUrl = suspendCancellableCoroutine<Uri?> { continuation ->
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null) {
                        continuation.resume(result, null)
                    } else {
                        continuation.resume(null, null)
                    }
                } else {
                    continuation.resume(null, null)
                }
            }
            continuation.invokeOnCancellation {
                uploadTask.cancel()
            }
        }
        Log.d("function", "saveImage url = ${downloadUrl}")
        emit(downloadUrl)
    }

    override suspend fun getImage(url: String): Flow<Bitmap> = flow {
        Log.d("function test", "getImage(${url})")
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val inputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)
        Log.d("function test", "getImage bitmap = ${bitmap}")
        emit(bitmap)
    }
}