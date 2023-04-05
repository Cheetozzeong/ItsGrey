package com.tntt.network.retrofit

import android.provider.ContactsContract.Data
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("/api/v1/itsGrey/test")
    suspend fun getData(): String

    @Multipart
    @POST("/api/v1/itsGrey/makeImage")
    suspend fun getSketch(
        @Part imageFile: MultipartBody.Part
    ): ResponseBody
}