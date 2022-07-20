package io.guardiaimperial.shoes4u.domain.model

data class User(
    var id: String = "",
    val name: String = "",
    val surname: String = "",
    val username: String = "",
    val city: String = "",
    val province: String = "",
    val email: String = ""
)
