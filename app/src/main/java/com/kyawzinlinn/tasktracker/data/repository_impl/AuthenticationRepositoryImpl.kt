package com.kyawzinlinn.tasktracker.data.repository_impl

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.Identity
import com.kyawzinlinn.tasktracker.data.repository.AuthenticationRepository
import com.kyawzinlinn.tasktracker.presentation.sign_in.GoogleAuthUiClient
import com.kyawzinlinn.tasktracker.presentation.sign_in.SignInResult
import com.kyawzinlinn.tasktracker.presentation.sign_in.UserData
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(context: Context) : AuthenticationRepository {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    override suspend fun signInWithGoogle() : IntentSender? = googleAuthUiClient.signIn()

    override fun getSignedUser(): UserData? = googleAuthUiClient.getSignedInUser()

    override suspend fun getSignInResultFromIntent(intent: Intent) : SignInResult = googleAuthUiClient.getSignInResultFromIntent(intent)

    override suspend fun signOut() {
        googleAuthUiClient.signOut()
    }
}