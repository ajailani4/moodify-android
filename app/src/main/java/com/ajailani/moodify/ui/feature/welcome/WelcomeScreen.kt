package com.ajailani.moodify.ui.feature.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 50.dp),
                text = stringResource(id = R.string.welcome_to_moodify),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.track_your_moods),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(id = R.drawable.illustration_welcome),
                contentDescription = "Welcome illustration"
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNavigateToLogin
            ) {
                Text(
                    modifier = Modifier.padding(3.dp),
                    text = stringResource(id = R.string.login)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = onNavigateToRegister
            ) {
                Text(
                    modifier = Modifier.padding(3.dp),
                    text = stringResource(id = R.string.register)
                )
            }
        }
    }
}