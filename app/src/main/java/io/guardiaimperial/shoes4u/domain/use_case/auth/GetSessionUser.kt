package io.guardiaimperial.shoes4u.domain.use_case.auth

import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import javax.inject.Inject

class GetSessionUser @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        result: (User?) -> Unit
    ) = repository.getSession(result)
}