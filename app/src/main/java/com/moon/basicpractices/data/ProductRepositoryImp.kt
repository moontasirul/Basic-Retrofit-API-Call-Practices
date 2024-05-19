package com.moon.basicpractices.data

import com.moon.basicpractices.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class ProductRepositoryImp(
    private val apiService: APIService
) : ProductRepository {

    override suspend fun getProductList(): Flow<Result<List<Product>>> {
        return flow {
            val productFromAPI = try {
                apiService.getProductsList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "An error happen of loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "An error happen of loading products"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "An error happen of loading products"))
                return@flow
            }

            emit(Result.Success(productFromAPI.products))
        }
    }
}