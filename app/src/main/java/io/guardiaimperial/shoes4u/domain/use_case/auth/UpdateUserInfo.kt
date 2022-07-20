package io.guardiaimperial.shoes4u.domain.use_case.auth

import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository


class UpdateUserInfo(
    private val repository: AuthRepository
) {
    fun invoke(
        user: User,
        result: (Response<String>) -> Unit
    ) = repository.updateUserInfo(user, result)
}
