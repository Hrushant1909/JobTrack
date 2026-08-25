package com.hrushant.jobtrack.ui.auth

sealed class RegistrationState {

    data object Idle : RegistrationState()

    data object Loading : RegistrationState()

    data class Success(
        val uid: String
    ) : RegistrationState()

    data class Error(
        val message: String
    ) : RegistrationState()
}