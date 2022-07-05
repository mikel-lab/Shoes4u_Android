package io.guardiaimperial.shoes4u.domain.model

data class User(
    var id: String? = null,
    var name: String? = null,
    val surname: String? = null,
    val username: String? = null,
    val city: String? = null,
    val province: String? = null,
    val email: String? = null
)
