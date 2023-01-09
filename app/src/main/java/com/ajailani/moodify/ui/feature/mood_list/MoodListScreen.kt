package com.ajailani.moodify.ui.feature.mood_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.common.component.CaptionImage
import com.ajailani.moodify.ui.common.component.MoodCard
import com.ajailani.moodify.ui.feature.mood_list.component.CircleButton
import com.ajailani.moodify.ui.feature.mood_list.component.MonthPickerDialog
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodListScreen(
    moodListViewModel: MoodListViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToMoodDetail: (String) -> Unit
) {
    val onEvent = moodListViewModel::onEvent
    val pagingMoods = moodListViewModel.pagingMoods.collectAsLazyPagingItems()
    val monthPickerDialogVis = moodListViewModel.monthPickerDialogVis
    val monthMenuExpanded = moodListViewModel.monthMenuExpanded
    val yearMenuExpanded = moodListViewModel.yearMenuExpanded
    val selectedMonth = moodListViewModel.selectedMonth
    val selectedYear = moodListViewModel.selectedYear
    val swipeRefreshing = moodListViewModel.swipeRefreshing

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
        // This is used because Compose Material 3 does not have rememberPullRefreshState() yet
        // like Compose Material 2
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = swipeRefreshing),
            onRefresh = {
                onEvent(MoodListEvent.OnSwipeRefresh(true))
                onEvent(MoodListEvent.GetPagingMoods)
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            Column(
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
                            contentDescription = "Previous month button",
                            onClick = {
                                onEvent(MoodListEvent.OnPreviousMonthClicked)
                                onEvent(MoodListEvent.GetPagingMoods)
                            }
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
                            contentDescription = "Next month button",
                            onClick = {
                                onEvent(MoodListEvent.OnNextMonthClicked)
                                onEvent(MoodListEvent.GetPagingMoods)
                            }
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(pagingMoods) { moodItem ->
                        moodItem?.let {
                            MoodCard(
                                moodItem = it,
                                onClick = { onNavigateToMoodDetail(it.id) }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                    // Handle pagingMoods state
                    pagingMoods.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(top = 170.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            loadState.append is LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth(Alignment.CenterHorizontally)
                                    )
                                }
                            }

                            loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached -> {
                                onEvent(MoodListEvent.OnSwipeRefresh(false))

                                if (itemCount < 1) {
                                    item {
                                        Box(
                                            modifier = Modifier.padding(top = 150.dp)
                                        ) {
                                            CaptionImage(
                                                modifier = Modifier.size(200.dp),
                                                image = painterResource(id = R.drawable.illustration_no_data),
                                                caption = stringResource(id = R.string.no_moods)
                                            )
                                        }
                                    }
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                onEvent(MoodListEvent.OnSwipeRefresh(false))

                                item {
                                    LaunchedEffect(snackbarHostState) {
                                        (loadState.append as LoadState.Error).error.localizedMessage?.let {
                                            snackbarHostState.showSnackbar(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Observe month picker dialog visibility
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