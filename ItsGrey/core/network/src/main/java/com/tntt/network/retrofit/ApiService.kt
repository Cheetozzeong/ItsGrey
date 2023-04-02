package com.tntt.network.retrofit

import android.provider.ContactsContract.Data
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("/api/v1/itsGrey/makeImage")
    suspend fun getData(): Response<Data>

    @Multipart
    @POST("/api/v1/itsGrey/makeImage")
    fun getSketch(@Part image: RequestBody): Call<ResponseBody>
}