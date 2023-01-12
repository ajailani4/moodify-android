package com.ajailani.moodify.ui.feature.add_edit_mood

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.feature.add_edit_mood.component.CustomTextField
import com.ajailani.moodify.ui.feature.add_edit_mood.component.MoodChoice
import com.ajailani.moodify.ui.feature.add_edit_mood.component.showDatePicker
import com.ajailani.moodify.ui.feature.add_edit_mood.component.showTimePicker
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMoodScreen(
    onNavigateUp: () -> Unit
) {
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
                    contentDescription = "Back filledIcon"
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Column {
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
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            TextField(
                                modifier = Modifier
                                    .width(150.dp)
                                    .clickable { context.showDatePicker {  } },
                                value = "",
                                onValueChange = {},
                                enabled = false,
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.DateRange,
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        contentDescription = "Date icon"
                                    )
                                },
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            TextField(
                                modifier = Modifier
                                    .width(90.dp)
                                    .clickable { context.showTimePicker {  } },
                                value = "",
                                onValueChange = {},
                                enabled = false,
                                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.AccessTime,
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        contentDescription = "Time icon"
                                    )
                                },
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = stringResource(id = R.string.what_activity),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = stringResource(id = R.string.activity),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(mainAxisSpacing = 8.dp) {
                        AssistChip(
                            label = {
                                Text(text = "Label")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Activity icon"
                                )
                            },
                            onClick = { /*TODO*/ }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.note),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomTextField(
                        value = "",
                        onValueChange = {}
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = stringResource(id = R.string.save)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}