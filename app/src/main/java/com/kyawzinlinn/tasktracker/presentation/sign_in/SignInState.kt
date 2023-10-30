package com.kyawzinlinn.tasktracker.presentation.sign_in

data class SignInState(
    val isSignedInSuccessful: Boolean = false,
    val signInError: String? = null
)
