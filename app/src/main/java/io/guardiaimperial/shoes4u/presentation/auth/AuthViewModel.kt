package io.guardiaimperial.shoes4u.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.use_case.auth.*
import io.guardiaimperial.shoes4u.utils.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    private val loginUser: LoginUser,
    private val logoutUser: LogoutUser,
    private val forgotPasswordUser: ForgotPasswordUser,
    private val getSessionUser: GetSessionUser
) : ViewModel() {

    private val _register = MutableLiveData<Response<String>>()
    val register: LiveData<Response<String>>
        get() = _register

    private val _login = MutableLiveData<Response<String>>()
    val login: LiveData<Response<String>>
        get() = _login

    private val _forgotPassword = MutableLiveData<Response<String>>()
    val forgotPassword: LiveData<Response<String>>
        get() = _forgotPassword

    fun register(
        email: String,
        password: String,
        user: User
    ) {
        _register.value = Response.Loading
        registerUser(
            email = email,
            password = password,
            user = user
        ) {
            _register.value = it
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        _login.value = Response.Loading
        loginUser(
            email = email,
            password = password
        ) {
            _login.value = it
        }
    }

    fun forgotPassword(
        email: String
    ) {
        _forgotPassword.value = Response.Loading
        forgotPasswordUser(
            email
        ) {
            _forgotPassword.value = it
        }
    }

    fun logout(
        result: () -> Unit
    ) {
        logoutUser(result)
    }

    fun getSession(
        result: (User?) -> Unit
    ) {
        getSessionUser(result)
    }
}