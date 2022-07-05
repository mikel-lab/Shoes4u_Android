package io.guardiaimperial.shoes4u.domain.use_case

import io.guardiaimperial.shoes4u.domain.model.Response
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository

class ForgotPassword(
    val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        result: (Response<String>) -> Unit
    ) = repository.forgotPassword(email, result)
}
