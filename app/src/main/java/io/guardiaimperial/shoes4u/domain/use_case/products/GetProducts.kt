package io.guardiaimperial.shoes4u.domain.use_case.products

import io.guardiaimperial.shoes4u.domain.model.Product
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.repository.ProductRepository
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(
        result: (Response<List<Product>>) -> Unit
    ) = repository.getProducts(result)
}