package io.guardiaimperial.shoes4u.domain.use_case

import io.guardiaimperial.shoes4u.domain.model.Response
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
