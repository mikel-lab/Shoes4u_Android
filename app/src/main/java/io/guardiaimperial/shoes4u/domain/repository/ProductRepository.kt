package io.guardiaimperial.shoes4u.domain.repository

import io.guardiaimperial.shoes4u.domain.model.Product
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.model.User

interface ProductRepository {
    fun getProducts(result: (Response<List<Product>>) -> Unit)
    fun addProduct(product: Product, result: (Response<Pair<Product,String>>) -> Unit)
}