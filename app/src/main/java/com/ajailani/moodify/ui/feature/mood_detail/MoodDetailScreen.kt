package com.ajailani.moodify.ui.feature.mood_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.util.Formatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodDetailScreen(
    moodDetailViewModel: MoodDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToAddEditMood: (String) -> Unit
) {
    val onEvent = moodDetailViewModel::onEvent
    val moodId = moodDetailViewModel.moodId
    val moodDetailState = moodDetailViewModel.moodDetailState
    val menuVisibility = moodDetailViewModel.menuVisibility

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.mood_detail))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(MoodDetailEvent.OnMenuVisibilityChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More option icon"
                        )
                    }
                    DropdownMenu(
                        expanded = menuVisibility,
                        onDismissRequest = {
                            onEvent(MoodDetailEvent.OnMenuVisibilityChanged(false))
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.edit))
                            },
                            onClick = {
                                moodId?.let {
                                    onNavigateToAddEditMood(it)
                                }

                                onEvent(MoodDetailEvent.OnMenuVisibilityChanged(false))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit icon"
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.delete))
                            },
                            onClick = {
                                onEvent(MoodDetailEvent.OnMenuVisibilityChanged(false))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete icon"
                                )
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            when (moodDetailState) {
                UIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is UIState.Success -> {
                    moodDetailState.data?.let { mood ->
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(top = 10.dp)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource(
                                    id = when (mood.mood) {
                                        1 -> R.drawable.ic_filled_terrible_mood
                                        2 -> R.drawable.ic_filled_bad_mood
                                        3 -> R.drawable.ic_filled_okay_mood
                                        4 -> R.drawable.ic_filled_good_mood
                                        5 -> R.drawable.ic_filled_excellent_mood
                                        else -> R.drawable.ic_filled_okay_mood
                                    }
                                ),
                                contentDescription = "Mood icon"
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = stringResource(id = R.string.activity_that_make),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            SectionCard(title = stringResource(id = R.string.activity)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(35.dp)
                                            .background(color = MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier.size(21.dp),
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(mood.activity.icon)
                                                .build(),
                                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                                            contentDescription = "Activity icon"
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(15.dp))
                                    Text(
                                        text = mood.activity.name,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            SectionCard(title = stringResource(id = R.string.note)) {
                                Text(
                                    text = mood.note ?: stringResource(id = R.string.no_note),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "${Formatter.formatDate(mood.date)}  •  ${mood.time}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
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
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}