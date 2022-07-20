package io.guardiaimperial.shoes4u.domain.use_case.products

import io.guardiaimperial.shoes4u.domain.repository.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before

internal class GetProductsTest{

    @RelaxedMockK
    private lateinit var productRepository: ProductRepository

    lateinit var getProductsUseCase: GetProducts

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
    }
}