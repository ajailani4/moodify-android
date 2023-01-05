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
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.common.component.MoodCard
import com.ajailani.moodify.ui.feature.home.component.ActivityCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val recommendedActivitiesState = homeViewModel.recommendedActivitiesState
    val moodsState = homeViewModel.moodsState

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add mood icon"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Header()
                Spacer(modifier = Modifier.height(30.dp))
                RecommendedActivitiesSection(
                    recommendedActivitiesState = recommendedActivitiesState,
                    snackbarHostState = snackbarHostState
                )
                Spacer(modifier = Modifier.height(30.dp))
                MyMoodsSection(
                    moodsState = moodsState,
                    snackbarHostState = snackbarHostState,
                    onViewAllClicked = {}
                )
            }
        }
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
    recommendedActivitiesState: UIState<List<Activity>>,
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
                recommendedActivitiesState.data?.let { activities ->
                    activities.forEach { activity ->
                        ActivityCard(
                            activity = activity
                        )

                        if (activity != activities.last()) {
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
            }

            is UIState.Fail -> {
                LaunchedEffect(snackbarHostState) {
                    recommendedActivitiesState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
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
    moodsState: UIState<List<MoodItem>>,
    snackbarHostState: SnackbarHostState,
    onViewAllClicked: () -> Unit
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
                moodsState.data?.let { moods ->
                    moods.forEach { moodItem ->
                        MoodCard(
                            moodItem = moodItem,
                            onClick = {}
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            is UIState.Fail -> {
                LaunchedEffect(snackbarHostState) {
                    moodsState.message?.let {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
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