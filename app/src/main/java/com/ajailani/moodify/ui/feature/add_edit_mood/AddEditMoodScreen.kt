package com.ajailani.moodify.ui.feature.add_edit_mood

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.common.SharedViewModel
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.common.component.ProgressBarWithBackground
import com.ajailani.moodify.ui.feature.add_edit_mood.component.CustomTextField
import com.ajailani.moodify.ui.feature.add_edit_mood.component.MoodChoice
import com.ajailani.moodify.ui.feature.add_edit_mood.component.showDatePicker
import com.ajailani.moodify.ui.feature.add_edit_mood.component.showTimePicker
import com.ajailani.moodify.util.Formatter
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMoodScreen(
    addEditMoodViewModel: AddEditMoodViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onNavigateUp: () -> Unit
) {
    val onEvent = addEditMoodViewModel::onEvent
    val moodId = addEditMoodViewModel.moodId
    val addMoodState = addEditMoodViewModel.addMoodState
    val editMoodState = addEditMoodViewModel.editMoodState
    val activitiesState = addEditMoodViewModel.activitiesState
    val moodDetailState = addEditMoodViewModel.moodDetailState
    val selectedMood = addEditMoodViewModel.selectedMood
    val selectedActivityName = addEditMoodViewModel.selectedActivityName
    val note = addEditMoodViewModel.note
    val date = addEditMoodViewModel.date
    val time = addEditMoodViewModel.time

    val onReloadedChanged = sharedViewModel::onReloadedChanged

    // Currently mood choices in this screen only available in English,
    // because it's better to use English so that the mood choices UI
    // can be adjusted to the screen width easily.
    val moodChoices = listOf(
        "Terrible",
        "Bad",
        "Okay",
        "Good",
        "Excellent"
    )

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
                                    isSelected = selectedMood == index + 1,
                                    onClick = {
                                        onEvent(AddEditMoodEvent.OnMoodChanged(index + 1))
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            TextField(
                                modifier = Modifier
                                    .width(160.dp)
                                    .clickable {
                                        context.showDatePicker {
                                            onEvent(AddEditMoodEvent.OnDateChanged(it))
                                        }
                                    },
                                value = Formatter.formatDate(date),
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
                                    .width(110.dp)
                                    .clickable {
                                        context.showTimePicker {
                                            onEvent(AddEditMoodEvent.OnTimeChanged(it))
                                        }
                                    },
                                value = time,
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
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Observe activities state
                    when (activitiesState) {
                        UIState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        is UIState.Success -> {
                            activitiesState.data?.let { activities ->
                                FlowRow(mainAxisSpacing = 8.dp) {
                                    activities.forEach { activity ->
                                        InputChip(
                                            selected = selectedActivityName == activity.name,
                                            label = {
                                                Text(text = activity.name)
                                            },
                                            leadingIcon = {
                                                AsyncImage(
                                                    modifier = Modifier.size(18.dp),
                                                    model = ImageRequest.Builder(context)
                                                        .data(activity.icon)
                                                        .build(),
                                                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                                    contentDescription = "Activity icon"
                                                )
                                            },
                                            onClick = {
                                                onEvent(
                                                    AddEditMoodEvent.OnActivityNameChanged(activity.name)
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        else -> {}
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.note),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomTextField(
                        value = note ?: "",
                        onValueChange = {
                            onEvent(AddEditMoodEvent.OnNoteChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (selectedMood != 0 && selectedActivityName.isNotEmpty() &&
                                date.isNotEmpty() && time.isNotEmpty()
                            ) {
                                if (moodId == null) {
                                    onEvent(AddEditMoodEvent.AddMood)
                                } else {
                                    onEvent(AddEditMoodEvent.EditMood)
                                }
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
                            text = stringResource(id = R.string.save)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // Observe mood detail state if moodId is not null
        if (moodId != null) {
            when (moodDetailState) {
                UIState.Loading -> ProgressBarWithBackground()

                is UIState.Success -> {
                    moodDetailState.data?.let { moodDetail ->
                        onEvent(AddEditMoodEvent.OnMoodChanged(moodDetail.mood))
                        onEvent(AddEditMoodEvent.OnActivityNameChanged(moodDetail.activity.name))
                        onEvent(AddEditMoodEvent.OnNoteChanged(moodDetail.note))
                        onEvent(AddEditMoodEvent.OnDateChanged(moodDetail.date))
                        onEvent(AddEditMoodEvent.OnTimeChanged(moodDetail.time))
                    }

                    onEvent(AddEditMoodEvent.Idle)
                }

                is UIState.Fail -> {
                    LaunchedEffect(snackbarHostState) {
                        moodDetailState.message?.let {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                is UIState.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        moodDetailState.message?.let {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                else -> {}
            }
        }

        // Observe add mood state
        when (addMoodState) {
            UIState.Loading -> ProgressBarWithBackground()

            is UIState.Success -> {
                LaunchedEffect(Unit) {
                    onReloadedChanged(true)
                    onNavigateUp()
                }
            }

            is UIState.Fail -> {
                LaunchedEffect(snackbarHostState) {
                    addMoodState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(snackbarHostState) {
                    addMoodState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }

        // Observe edit mood state
        when (editMoodState) {
            UIState.Loading -> ProgressBarWithBackground()

            is UIState.Success -> {
                LaunchedEffect(Unit) {
                    onReloadedChanged(true)
                    onNavigateUp()
                }
            }

            is UIState.Fail -> {
                LaunchedEffect(snackbarHostState) {
                    editMoodState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(snackbarHostState) {
                    editMoodState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}