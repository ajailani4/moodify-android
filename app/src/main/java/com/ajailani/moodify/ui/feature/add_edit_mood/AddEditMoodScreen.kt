package com.ajailani.moodify.ui.feature.add_edit_mood

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.feature.add_edit_mood.component.MoodChoice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMoodScreen() {
    // Currently mood choices only available in English,
    // because the mood choices UI need to be adjusted to the screen width
    val moodChoices = listOf(
        "Terrible",
        "Bad",
        "Okay",
        "Good",
        "Excellent"
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                },
                actions = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.how_are_you),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        moodChoices.forEachIndexed { index, moodName ->
                            MoodChoice(
                                mood = index + 1,
                                moodName = moodName,
                                isSelected = false,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}