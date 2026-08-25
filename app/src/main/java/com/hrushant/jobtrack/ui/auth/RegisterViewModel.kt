package com.hrushant.jobtrack.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.jobtrack.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationState>(
        RegistrationState.Idle
    )

    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun registerUser(email: String, password: String) {

        viewModelScope.launch {

            _registrationState.value = RegistrationState.Loading

            val result = repository.registerUser(email, password)

            if (result.isSuccess) {

                val uid = result.getOrNull()

                _registrationState.value =
                    RegistrationState.Success(uid!!)

            } else {

                val error = result.exceptionOrNull()

                _registrationState.value =
                    RegistrationState.Error(
                        error?.message ?: "Registration failed"
                    )
            }
        }
    }
}