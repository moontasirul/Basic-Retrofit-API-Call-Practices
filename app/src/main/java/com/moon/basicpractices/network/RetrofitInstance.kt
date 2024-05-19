package com.moon.basicpractices.network

import com.moon.basicpractices.data.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofitClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()


    val productAPI: APIService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(APIService.BASE_URL)
        .client(retrofitClient)
        .build()
        .create(APIService::class.java)
}