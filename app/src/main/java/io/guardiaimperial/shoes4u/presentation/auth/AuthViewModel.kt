package io.guardiaimperial.shoes4u.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.model.Response
import io.guardiaimperial.shoes4u.domain.use_case.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    private val loginUser: LoginUser,
    private val logoutUser: LogoutUser,
    private val forgotPasswordUser: ForgotPasswordUser
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

    /** Llama a la función "registerUser" del repositorio.*/
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

    /** Llama a la función "loginUser" del repositorio.*/
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

    /** Llama a la función "forgotPassword" del repositorio.*/
    fun forgotPassword(
        email:String
    ) {
        _forgotPassword.value = Response.Loading
        forgotPasswordUser(
            email
        ){
            _forgotPassword.value = it
        }
    }

    /** Llama a la función "logout" del repositorio.*/
    fun logout(
        result: () -> Unit
    ) {
        logoutUser(result)
    }
}