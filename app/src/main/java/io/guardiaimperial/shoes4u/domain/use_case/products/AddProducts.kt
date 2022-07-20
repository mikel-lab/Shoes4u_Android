package io.guardiaimperial.shoes4u.domain.use_case.products

import io.guardiaimperial.shoes4u.domain.model.Product
import io.guardiaimperial.shoes4u.domain.repository.ProductRepository
import io.guardiaimperial.shoes4u.utils.Response
import javax.inject.Inject

class AddProduct @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(
        product: Product,
        result: (Response<Pair<Product,String>>) -> Unit
    ) = repository.addProduct(product, result)
}