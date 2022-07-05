package io.guardiaimperial.shoes4u.domain.use_case

data class UseCases(
    val forgotPassword: ForgotPassword,
    val loginUser: LoginUser,
    val logoutUser: LogoutUser,
    val updateUserInfo: UpdateUserInfo,
    val registerUser: RegisterUser
)
