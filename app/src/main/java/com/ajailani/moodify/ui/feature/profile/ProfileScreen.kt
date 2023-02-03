package com.ajailani.moodify.ui.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.common.component.ProgressBarWithBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToWelcome: () -> Unit
) {
    val onEvent = profileViewModel::onEvent
    val userProfileState = profileViewModel.userProfileState
    val logoutState = profileViewModel.logoutState
    val logoutDialogVisibility = profileViewModel.logoutDialogVisibility

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.profile),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(30.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    when (userProfileState) {
                        UIState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UIState.Success -> {
                            userProfileState.data?.let { userProfile ->
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = userProfile.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = userProfile.username,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = userProfile.email,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        is UIState.Fail -> {
                            LaunchedEffect(snackbarHostState) {
                                userProfileState.message?.let {
                                    snackbarHostState.showSnackbar(it)
                                }
                            }
                        }

                        is UIState.Error -> {
                            LaunchedEffect(snackbarHostState) {
                                userProfileState.message?.let {
                                    snackbarHostState.showSnackbar(it)
                                }
                            }
                        }

                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(ProfileEvent.OnLogoutDialogVisChanged(true))
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(3.dp),
                        text = stringResource(id = R.string.logout)
                    )
                }
            }
        }

        // Observe logout state
        when (logoutState) {
            UIState.Loading -> ProgressBarWithBackground()

            is UIState.Success -> onNavigateToWelcome()

            is UIState.Fail -> {
                LaunchedEffect(snackbarHostState) {
                    logoutState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(snackbarHostState) {
                    logoutState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }

    if (logoutDialogVisibility) {
        AlertDialog(
            onDismissRequest = {
                onEvent(ProfileEvent.OnLogoutDialogVisChanged(false))
            },
            title = {
                Text(text = stringResource(id = R.string.logout))
            },
            text = {
                Text(text = stringResource(id = R.string.logout_confirm_msg))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(ProfileEvent.Logout)
                        onEvent(ProfileEvent.OnLogoutDialogVisChanged(false))
                    }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent(ProfileEvent.OnLogoutDialogVisChanged(false))
                    }
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
        )
    }
}