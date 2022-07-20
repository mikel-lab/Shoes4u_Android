package io.guardiaimperial.shoes4u.domain.use_case.auth

import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        result: () -> Unit
    ) = repository.logout(result)
}
