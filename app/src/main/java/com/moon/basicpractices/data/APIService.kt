package com.moon.basicpractices.data

import com.moon.basicpractices.data.model.Products
import retrofit2.http.GET

interface APIService {

    @GET("products")
    suspend fun getProductsList(): Products

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}