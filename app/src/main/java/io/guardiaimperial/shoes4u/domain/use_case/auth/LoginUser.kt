package io.guardiaimperial.shoes4u.domain.use_case.auth

import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        result: (Response<String>) -> Unit
    ) = repository.loginUser(email, password, result)
}
