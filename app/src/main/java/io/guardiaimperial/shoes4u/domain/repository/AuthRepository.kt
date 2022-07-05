package io.guardiaimperial.shoes4u.domain.repository

import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.model.Response

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (Response<String>) -> Unit)
    fun updateUserInfo(user: User, result: (Response<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (Response<String>) -> Unit)
    fun forgotPassword(email: String, result: (Response<String>) -> Unit)
    fun logout(result: () -> Unit)
}