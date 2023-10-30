package com.kyawzinlinn.tasktracker.data.repository

import android.content.Intent
import android.content.IntentSender
import com.kyawzinlinn.tasktracker.presentation.sign_in.SignInResult
import com.kyawzinlinn.tasktracker.presentation.sign_in.UserData

interface AuthenticationRepository {
    suspend fun signInWithGoogle(): IntentSender?
    suspend fun getSignInResultFromIntent(intent: Intent): SignInResult
    fun getSignedUser(): UserData?
    suspend fun signOut(): Unit
}