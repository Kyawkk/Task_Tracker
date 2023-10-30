package com.kyawzinlinn.tasktracker.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kyawzinlinn.tasktracker.R

@Composable
fun LoginScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(state.signInError) {
        state.signInError?.let {
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.log_in_caption),
            modifier = Modifier.padding(top = 32.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            onClick = onSignInClick
        ) {
            Icon(painter = painterResource(R.drawable.logo_google), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.sign_in_with_google))
        }
    }
}