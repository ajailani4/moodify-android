package com.ajailani.moodify.ui.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
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
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "George",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "george_z",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "george@email.com",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        modifier = Modifier.padding(3.dp),
                        text = stringResource(id = R.string.logout)
                    )
                }
            }
        }
    }
}