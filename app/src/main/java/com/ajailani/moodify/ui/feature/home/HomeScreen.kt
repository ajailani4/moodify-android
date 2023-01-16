package com.ajailani.moodify.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajailani.moodify.R
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.ui.common.SharedViewModel
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.common.component.CaptionImage
import com.ajailani.moodify.ui.common.component.MoodCard
import com.ajailani.moodify.ui.feature.home.component.ActivityCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onNavigateToMoodList: () -> Unit,
    onNavigateToMoodDetail: (String) -> Unit,
    onNavigateToAddEditMood: () -> Unit
) {
    val onEvent = homeViewModel::onEvent
    val recommendedActivitiesState = homeViewModel.recommendedActivitiesState
    val moodsState = homeViewModel.moodsState
    val swipeRefreshing = homeViewModel.swipeRefreshing

    val reloaded = sharedViewModel.reloaded
    val onReloadedChanged = sharedViewModel::onReloadedChanged

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddEditMood) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add mood icon"
                )
            }
        }
    ) { innerPadding ->
        // This is used because Compose Material 3 does not have rememberPullRefreshState() yet
        // like Compose Material 2
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = swipeRefreshing),
            onRefresh = {
                onEvent(HomeEvent.OnSwipeRefresh(true))
                onEvent(HomeEvent.GetRecommendedActivities)
                onEvent(HomeEvent.GetMoods)
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
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Header()
                    Spacer(modifier = Modifier.height(30.dp))
                    RecommendedActivitiesSection(
                        onEvent = onEvent,
                        recommendedActivitiesState = recommendedActivitiesState,
                        onReloadedChanged = onReloadedChanged,
                        snackbarHostState = snackbarHostState
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    MyMoodsSection(
                        onEvent = onEvent,
                        moodsState = moodsState,
                        snackbarHostState = snackbarHostState,
                        onReloadedChanged = onReloadedChanged,
                        onViewAllClicked = onNavigateToMoodList,
                        onNavigateToMoodDetail = onNavigateToMoodDetail
                    )
                }
            }
        }
    }

    // Observe reloaded state from SharedViewModel
    if (reloaded) {
        onEvent(HomeEvent.GetRecommendedActivities)
        onEvent(HomeEvent.GetMoods)
    }
}

@Composable
private fun Header() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.moodify_logo),
            contentDescription = "App logo"
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun RecommendedActivitiesSection(
    onEvent: (HomeEvent) -> Unit,
    recommendedActivitiesState: UIState<List<Activity>>,
    onReloadedChanged: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Column {
        Text(
            text = stringResource(id = R.string.recommended_activities),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        when (recommendedActivitiesState) {
            UIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                onEvent(HomeEvent.OnSwipeRefresh(false))
                onReloadedChanged(false)

                recommendedActivitiesState.data?.let { activities ->
                    if (activities.isNotEmpty()) {
                        activities.forEach { activity ->
                            ActivityCard(
                                activity = activity
                            )

                            if (activity != activities.last()) {
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    } else {
                        CaptionImage(
                            modifier = Modifier.size(170.dp),
                            image = painterResource(id = R.drawable.illustration_no_data),
                            caption = stringResource(id = R.string.no_recommendation_yet)
                        )
                    }
                }
            }

            is UIState.Fail -> {
                onEvent(HomeEvent.OnSwipeRefresh(false))
                onReloadedChanged(false)

                LaunchedEffect(snackbarHostState) {
                    recommendedActivitiesState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                onEvent(HomeEvent.OnSwipeRefresh(false))
                onReloadedChanged(false)

                LaunchedEffect(snackbarHostState) {
                    recommendedActivitiesState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun MyMoodsSection(
    onEvent: (HomeEvent) -> Unit,
    moodsState: UIState<List<MoodItem>>,
    snackbarHostState: SnackbarHostState,
    onReloadedChanged: (Boolean) -> Unit,
    onViewAllClicked: () -> Unit,
    onNavigateToMoodDetail: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.my_moods),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            ClickableText(
                text = buildAnnotatedString { append(stringResource(id = R.string.view_all)) },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                ),
                onClick = { onViewAllClicked() }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        when (moodsState) {
            UIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                onEvent(HomeEvent.OnSwipeRefresh(false))
                onReloadedChanged(false)

                moodsState.data?.let { moods ->
                    if (moods.isNotEmpty()) {
                        moods.forEach { moodItem ->
                            MoodCard(
                                moodItem = moodItem,
                                onClick = { onNavigateToMoodDetail(moodItem.id) }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    } else {
                        CaptionImage(
                            modifier = Modifier.size(170.dp),
                            image = painterResource(id = R.drawable.illustration_add_mood),
                            caption = stringResource(id = R.string.no_moods)
                        )
                    }
                }
            }

            is UIState.Fail -> {
                onEvent(HomeEvent.OnSwipeRefresh(false))
                onReloadedChanged(false)

                LaunchedEffect(snackbarHostState) {
                    moodsState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                onEvent(HomeEvent.OnSwipeRefresh(false))
                onReloadedChanged(false)

                LaunchedEffect(snackbarHostState) {
                    moodsState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}