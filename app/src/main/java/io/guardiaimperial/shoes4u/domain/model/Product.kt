package io.guardiaimperial.shoes4u.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: String = "",
    val category: String? = "",
    val imageUrl: String? = "",
    val description: String = "",
    val name: String = "",
    val price: Double? = null
) : Parcelable
