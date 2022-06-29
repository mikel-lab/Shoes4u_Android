package io.guardiaimperial.shoes4u.data.repository

import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.utils.UiState

interface AuthRepositoryInterface {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
}