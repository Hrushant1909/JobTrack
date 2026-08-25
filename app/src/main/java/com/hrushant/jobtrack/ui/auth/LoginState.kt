package com.hrushant.jobtrack.ui.auth

sealed class LoginState {

    data object Idle : LoginState()

    data object Loading : LoginState()

    data class Success(
        val uid: String
    ) : LoginState()

    data object PasswordResetSent : LoginState()

    data class Error(
        val message: String
    ) : LoginState()
}