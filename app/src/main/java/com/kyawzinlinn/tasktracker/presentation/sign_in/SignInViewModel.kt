package com.kyawzinlinn.tasktracker.presentation.sign_in

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.tasktracker.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {
    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    suspend fun signInWithGoogle() = authenticationRepository.signInWithGoogle()

    fun getSignInResultFromIntent(intent: Intent) {
        viewModelScope.launch {
            val signInResult = authenticationRepository.getSignInResultFromIntent(intent)
            CoroutineScope(Dispatchers.Main).launch {
                _signInState.update {
                    it.copy(
                        isSignedInSuccessful = signInResult.data != null,
                        signInError = signInResult.errorMessage
                    )
                }
            }
        }
    }

    fun signOut() = viewModelScope.launch { authenticationRepository.signOut() }

    fun onSignInResult(result: SignInResult) {
        _signInState.update {
            it.copy(
                isSignedInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState(){
        _signInState.update { SignInState() }
    }
}