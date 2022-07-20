package io.guardiaimperial.shoes4u.domain.use_case.auth

import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        user: User,
        result: (Response<String>) -> Unit
    ) = repository.registerUser(email, password, user, result)
}
