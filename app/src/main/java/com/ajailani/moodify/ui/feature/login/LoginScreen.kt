package com.ajailani.moodify.ui.feature.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.common.component.ProgressBarWithBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val onEvent = loginViewModel::onEvent
    val loginState = loginViewModel.loginState
    val username = loginViewModel.username
    val password = loginViewModel.password
    val passwordVisibility = loginViewModel.passwordVisibility

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(
                modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back icon"
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = {
                        onEvent(LoginEvent.OnUsernameChanged(it))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.username))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Username icon"
                        )
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        onEvent(LoginEvent.OnPasswordChanged(it))
                    },
                    label = {
                        Text(text = stringResource(id = R.string.password))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Email icon"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onEvent(LoginEvent.OnPasswordVisibilityChanged)
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisibility) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = "Password visibility icon"
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()
                        ) {
                            onEvent(LoginEvent.Login)
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    context.getString(R.string.fill_the_form)
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = stringResource(id = R.string.login)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.have_no_account))
                        append(" ")

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append(stringResource(id = R.string.register_here))
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    onClick = { onNavigateToRegister() }
                )
            }
        }

        // Observe login state
        when (loginState) {
            UIState.Loading -> ProgressBarWithBackground()

            is UIState.Success -> {}

            is UIState.Fail -> {
                LaunchedEffect(snackbarHostState) {
                    loginState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(snackbarHostState) {
                    loginState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}