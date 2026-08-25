package com.hrushant.jobtrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.jobtrack.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(
        LoginState.Idle
    )

    val loginState: StateFlow<LoginState> = _loginState

    fun loginUser(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _loginState.value = LoginState.Loading

            val result = repository.loginUser(
                email,
                password
            )

            if (result.isSuccess) {

                val uid = result.getOrNull()

                if (uid != null) {
                    _loginState.value =
                        LoginState.Success(uid)
                } else {
                    _loginState.value =
                        LoginState.Error("UID is null")
                }

            } else {

                _loginState.value =
                    LoginState.Error(
                        result.exceptionOrNull()?.message
                            ?: "Login failed"
                    )
            }
        }
    }

    fun sendPasswordResetEmail(email: String) {

        viewModelScope.launch {

            val result = repository.sendPasswordResetEmail(email)

            if (result.isSuccess) {

                _loginState.value =
                    LoginState.PasswordResetSent

            } else {

                _loginState.value =
                    LoginState.Error(
                        result.exceptionOrNull()?.message
                            ?: "Failed to send password reset email"
                    )
            }
        }
    }

    fun logoutUser(){
        repository.logout()
    }
}