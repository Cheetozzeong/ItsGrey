package com.tntt.network.retrofit

import android.provider.ContactsContract.Data
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("")
    suspend fun getData(): Response<Data>
}