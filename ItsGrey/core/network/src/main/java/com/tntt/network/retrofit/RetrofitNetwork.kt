package com.tntt.network.retrofit

import com.squareup.okhttp.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitNetwork {

    private var retrofit: Retrofit? = null

    fun getApiService(): ApiService {
        if(retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://www.traceoflight.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}