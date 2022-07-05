package io.guardiaimperial.shoes4u.domain.use_case

import io.guardiaimperial.shoes4u.domain.repository.AuthRepository

class LogoutUser(
    private val repository: AuthRepository
) {
    operator fun invoke(
        result: () -> Unit
    ) = repository.logout(result)
}
