package io.guardiaimperial.shoes4u.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.guardiaimperial.shoes4u.domain.model.Product
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.repository.ProductRepository
import io.guardiaimperial.shoes4u.utils.FireStoreCollection

class ProductRepositoryImpl(
    private val database : FirebaseFirestore
) : ProductRepository {

    override fun getProducts(result: (Response<List<Product>>) -> Unit) {
        database.collection(FireStoreCollection.PRODUCT)
            .get()
            .addOnSuccessListener {
                val products = arrayListOf<Product>()
                for (document in it) {
                    val product = document.toObject(Product::class.java)
                    products.add(product)
                }
                result.invoke(Response.Success(products))
            }
            .addOnFailureListener {
                result.invoke(Response.Failure(it.localizedMessage))
            }
    }

    override fun addProduct(product: Product, result: (Response<Pair<Product,String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.PRODUCT).document()
        product.id = document.id
        document
            .set(product)
            .addOnSuccessListener {
                result.invoke(
                    Response.Success(Pair(product,"Producto añadido"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Response.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}