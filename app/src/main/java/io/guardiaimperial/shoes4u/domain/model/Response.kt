package io.guardiaimperial.shoes4u.domain.model

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
        ): Response<T>()

    data class Failure(
        val error: String?
        ): Response<Nothing>()
}