package com.kyawzinlinn.tasktracker.presentation.sign_in

data class SignInState(
    val isSignedInSuccessful: Boolean = false,
    val signInError: String? = null
)

sealed class AuthState {
    object IDLE: AuthState()
    object LOADING : AuthState()
    data class SUCCESS (val signInResult: SignInResult): AuthState()
    data class ERROR (val message: String): AuthState()
}
