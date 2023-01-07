package com.ajailani.moodify.ui.feature.mood_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.feature.mood_list.component.CircleButton
import com.ajailani.moodify.ui.feature.mood_list.component.MonthPickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodListScreen(
    moodListViewModel: MoodListViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val onEvent = moodListViewModel::onEvent
    val monthPickerDialogVis = moodListViewModel.monthPickerDialogVis
    val monthMenuExpanded = moodListViewModel.monthMenuExpanded
    val yearMenuExpanded = moodListViewModel.yearMenuExpanded
    val selectedMonth = moodListViewModel.selectedMonth
    val selectedYear = moodListViewModel.selectedYear

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.my_moods))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Surface(color = MaterialTheme.colorScheme.background) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleButton(
                        icon = Icons.Default.ChevronLeft,
                        onClick = {}
                    )
                    ClickableText(
                        text = buildAnnotatedString {
                            append("${stringArrayResource(id = R.array.months)[selectedMonth]} $selectedYear")
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        onClick = {
                            onEvent(MoodListEvent.OnMonthPickerDialogVisChanged(true))
                        }
                    )
                    CircleButton(
                        icon = Icons.Default.ChevronRight,
                        onClick = {}
                    )
                }
            }
        }

        if (monthPickerDialogVis) {
            MonthPickerDialog(
                onEvent = onEvent,
                monthMenuExpanded = monthMenuExpanded,
                yearMenuExpanded = yearMenuExpanded,
                selectedMonth = selectedMonth,
                selectedYear = selectedYear
            )
        }
    }
}