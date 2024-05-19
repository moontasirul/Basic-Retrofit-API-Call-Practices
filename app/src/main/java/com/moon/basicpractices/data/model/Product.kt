package com.moon.basicpractices.data.model

data class Product(val brand: String,
                   val category: String,
                   val description: String,
                   val discountPercentage: Double,
                   val id: Int,
                   val images: List<String>,
                   val price: Int,
                   val rating: Double,
                   val stock: Int,
                   val thumbnail: String,
                   val title: String
)

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)
