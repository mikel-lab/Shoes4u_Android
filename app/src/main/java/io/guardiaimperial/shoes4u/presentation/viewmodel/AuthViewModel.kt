package io.guardiaimperial.shoes4u.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.guardiaimperial.shoes4u.data.repository.AuthRepository
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.utils.UiState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun register(
        email: String,
        password: String,
        user: User
    ) {
        _register.value = UiState.Loading
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ) {
            _register.value = it
        }
    }
}