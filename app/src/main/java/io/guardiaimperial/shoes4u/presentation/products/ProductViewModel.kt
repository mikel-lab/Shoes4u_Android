package io.guardiaimperial.shoes4u.presentation.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.guardiaimperial.shoes4u.domain.model.Product
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.use_case.products.AddProduct
import io.guardiaimperial.shoes4u.domain.use_case.products.GetProducts
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val getProducts: GetProducts,
    val addProducts: AddProduct

    ): ViewModel() {

    private val _getProducts = MutableLiveData<Response<List<Product>>>()
    val getProduct: LiveData<Response<List<Product>>>
        get() = _getProducts

    private val _addProduct = MutableLiveData<Response<Pair<Product,String>>>()
    val addProduct: LiveData<Response<Pair<Product,String>>>
        get() = _addProduct

    fun getProducts(user: User?) {
        _getProducts.value = Response.Loading
            getProducts(
            ) {
                _getProducts.value = it
            }
    }

    fun addProduct(product: Product){
        _addProduct.value = Response.Loading
        addProducts(product) { _addProduct.value = it }
    }
}