package io.guardiaimperial.shoes4u.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.guardiaimperial.shoes4u.domain.repository.AuthRepository
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.domain.model.Response
import io.guardiaimperial.shoes4u.domain.use_case.UseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val useCases: UseCases
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
        useCases.registerUser(
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
        useCases.loginUser(
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
        useCases.forgotPassword(email){
            _forgotPassword.value = it
        }
    }

    /** Llama a la función "logout" del repositorio.*/
    fun logout(
        result: () -> Unit
    ) {
        useCases.logoutUser(result)
    }
}