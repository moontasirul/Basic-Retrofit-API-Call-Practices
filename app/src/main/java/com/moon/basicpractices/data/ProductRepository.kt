package com.moon.basicpractices.data

import com.moon.basicpractices.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductList(): Flow<Result<List<Product>>>
}