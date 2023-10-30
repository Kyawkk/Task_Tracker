package com.kyawzinlinn.tasktracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kyawzinlinn.tasktracker.presentation.sign_in.LoginScreen
import com.kyawzinlinn.tasktracker.presentation.sign_in.SignInState

@Composable
fun TaskTrackerApp(
    signInState: SignInState,
    onSignInClick: () -> Unit
) {
    TaskTrackerAppContent(
        signInState, onSignInClick
    )
}

@Composable
fun TaskTrackerAppContent(
    signInState: SignInState,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier.fillMaxSize()
    ) {
        LoginScreen(state = signInState,onSignInClick = onSignInClick)
    }
}
