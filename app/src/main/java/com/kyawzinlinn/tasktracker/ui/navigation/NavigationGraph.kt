package com.kyawzinlinn.tasktracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCompositionContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyawzinlinn.tasktracker.presentation.home.HomeScreen
import com.kyawzinlinn.tasktracker.presentation.sign_in.LoginScreen
import com.kyawzinlinn.tasktracker.presentation.sign_in.SignInState

@Composable
fun TaskTrackerNavHost(
    signInState: SignInState,
    onSignInClick: () -> Unit,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.LOGIN.name
    ) {
        composable(route = ScreenRoute.LOGIN.name) {
            LoginScreen(
                state = signInState,
                onSignInClick = onSignInClick
            )
        }

        composable(route = ScreenRoute.HOME.name) {
            HomeScreen()
        }
    }
}