package com.kyawzinlinn.tasktracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.kyawzinlinn.tasktracker.presentation.sign_in.GoogleAuthUiClient
import com.kyawzinlinn.tasktracker.presentation.sign_in.SignInViewModel
import com.kyawzinlinn.tasktracker.ui.navigation.ScreenRoute
import com.kyawzinlinn.tasktracker.ui.navigation.TaskTrackerNavHost
import com.kyawzinlinn.tasktracker.ui.theme.TaskTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SignInViewModel = viewModel()
            val state by viewModel.signInState.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            viewModel.getSignInResultFromIntent(result.data ?: return@launch)
                        }
                    }
                }
            )

            LaunchedEffect(state) {
                if (state.isSignedInSuccessful) {
                    Toast.makeText(applicationContext, state.isSignedInSuccessful.toString(), Toast.LENGTH_LONG)
                        .show()
                    navController.navigate(ScreenRoute.HOME.name)
                    viewModel.resetState()
                } else {
                    Toast.makeText(applicationContext, state.signInError ?: "", Toast.LENGTH_LONG)
                        .show()
                }
            }

            TaskTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskTrackerNavHost(
                        viewModel = viewModel,
                        signInState = state,
                        navController = navController,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInSender = viewModel.signInWithGoogle()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInSender ?: return@launch
                                    ).build()
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}