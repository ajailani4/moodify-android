package com.ajailani.moodify.ui.feature.statistic

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajailani.moodify.R
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.common.component.CaptionImage
import com.ajailani.moodify.ui.feature.statistic.component.ChartLegend
import com.ajailani.moodify.ui.theme.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(
    statisticViewModel: StatisticViewModel = hiltViewModel()
) {
    val onEvent = statisticViewModel::onEvent
    val moodPercentageState = statisticViewModel.moodPercentageState
    val frequentActivitiesState = statisticViewModel.frequentActivitiesState
    val swipeRefreshing = statisticViewModel.swipeRefreshing

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        // This is used because Compose Material 3 does not have rememberPullRefreshState() yet
        // like Compose Material 2
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = swipeRefreshing),
            onRefresh = {
                onEvent(StatisticEvent.OnSwipeRefresh(true))
                onEvent(StatisticEvent.GetMoodPercentage)
                onEvent(StatisticEvent.GetFrequentActivities)
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
                    Text(
                        text = stringResource(id = R.string.statistic),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    MoodPercentageSection(
                        onEvent = onEvent,
                        moodPercentageState = moodPercentageState,
                        snackbarHostState = snackbarHostState
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    FrequentActivitiesSection(
                        context = context,
                        onEvent = onEvent,
                        frequentActivitiesState = frequentActivitiesState,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

@Composable
private fun MoodPercentageSection(
    onEvent: (StatisticEvent) -> Unit,
    moodPercentageState: UIState<MoodPercentage>,
    snackbarHostState: SnackbarHostState
) {
    val chartLabels = listOf(
        Pair(ExcellentMoodGreen, stringResource(id = R.string.excellent)),
        Pair(GoodMoodGreen, stringResource(id = R.string.good)),
        Pair(OkayMoodBlue, stringResource(id = R.string.okay)),
        Pair(BadMoodOrange, stringResource(id = R.string.bad)),
        Pair(TerribleMoodRed, stringResource(id = R.string.terrible))
    )

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.mood_percentage),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))

            when (moodPercentageState) {
                UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UIState.Success -> {
                    onEvent(StatisticEvent.OnSwipeRefresh(false))

                    moodPercentageState.data?.let { moodPercentage ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AndroidView(
                                factory = { context ->
                                    PieChart(context).apply {
                                        layoutParams = LinearLayout.LayoutParams(500, 500)
                                        description.isEnabled = false
                                        isDrawHoleEnabled = true
                                        legend.isEnabled = false
                                    }
                                },
                                update = { pieChart ->
                                    val pieEntries = mutableListOf<PieEntry>()

                                    pieEntries.add(PieEntry(moodPercentage.excellent))
                                    pieEntries.add(PieEntry(moodPercentage.good))
                                    pieEntries.add(PieEntry(moodPercentage.okay))
                                    pieEntries.add(PieEntry(moodPercentage.bad))
                                    pieEntries.add(PieEntry(moodPercentage.terrible))

                                    val pieDataSet = PieDataSet(pieEntries, "")

                                    pieDataSet.apply {
                                        colors = chartLabels.map { it.first.toArgb() }
                                        valueTextColor = android.graphics.Color.WHITE
                                        valueTextSize = 11f
                                        valueTypeface = Typeface.DEFAULT_BOLD
                                    }

                                    val pieData = PieData(pieDataSet)
                                    pieData.setValueFormatter(object : ValueFormatter() {
                                        override fun getFormattedValue(value: Float): String {
                                            return if (value == 0f) {
                                                ""
                                            } else {
                                                "$value%"
                                            }
                                        }
                                    })

                                    pieChart.data = pieData
                                    pieChart.invalidate()
                                }
                            )
                            Column {
                                chartLabels.forEach { legend ->
                                    ChartLegend(
                                        color = legend.first,
                                        text = legend.second
                                    )

                                    if (legend != chartLabels.last()) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                is UIState.Fail -> {
                    onEvent(StatisticEvent.OnSwipeRefresh(false))

                    moodPercentageState.message?.let {
                        CaptionImage(
                            modifier = Modifier.size(150.dp),
                            image = painterResource(id = R.drawable.illustration_no_data),
                            caption = it
                        )
                    }
                }

                is UIState.Error -> {
                    onEvent(StatisticEvent.OnSwipeRefresh(false))

                    LaunchedEffect(snackbarHostState) {
                        moodPercentageState.message?.let {
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
private fun FrequentActivitiesSection(
    context: Context,
    onEvent: (StatisticEvent) -> Unit,
    frequentActivitiesState: UIState<List<FrequentActivity>>,
    snackbarHostState: SnackbarHostState
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(id = R.string.frequent_activities),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(20.dp))

            when (frequentActivitiesState) {
                UIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UIState.Success -> {
                    onEvent(StatisticEvent.OnSwipeRefresh(false))

                    frequentActivitiesState.data?.let { frequentActivities ->
                        if (frequentActivities.isNotEmpty()) {
                            AndroidView(
                                factory = { context ->
                                    BarChart(context).apply {
                                        layoutParams = LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            600
                                        )
                                        description.isEnabled = false
                                        legend.isEnabled = false
                                    }
                                },
                                update = { barChart ->
                                    val labels = frequentActivities.map { it.activity }
                                    val barEntries = mutableListOf<BarEntry>()

                                    frequentActivities.forEachIndexed { index, activity ->
                                        barEntries.add(
                                            BarEntry(
                                                index.toFloat(),
                                                activity.count.toFloat()
                                            )
                                        )
                                    }

                                    val barDataSet = BarDataSet(barEntries, "")
                                    barDataSet.color =
                                        ContextCompat.getColor(context, R.color.tertiary)
                                    barDataSet.setDrawValues(false)

                                    val barData = BarData(barDataSet)

                                    barChart.setDrawValueAboveBar(false)
                                    barChart.xAxis.apply {
                                        valueFormatter = IndexAxisValueFormatter(labels)
                                        position = XAxis.XAxisPosition.BOTTOM
                                        granularity = 1f
                                        textSize = 12f
                                        textColor = ContextCompat.getColor(
                                            context,
                                            R.color.onSurfaceVariant
                                        )
                                        axisLineWidth = 1f
                                        setDrawGridLines(false)
                                    }
                                    barChart.axisLeft.apply {
                                        granularity = 1f
                                        isGranularityEnabled = true
                                        textSize = 12f
                                        textColor = ContextCompat.getColor(
                                            context,
                                            R.color.onSurfaceVariant
                                        )
                                        axisLineWidth = 1f
                                        setDrawGridLines(false)
                                    }
                                    barChart.axisRight.apply {
                                        setDrawGridLines(false)
                                        setDrawLabels(false)
                                        setDrawAxisLine(false)
                                    }
                                    barChart.data = barData
                                    barChart.invalidate()
                                }
                            )
                        }
                    }
                }

                is UIState.Fail -> {
                    onEvent(StatisticEvent.OnSwipeRefresh(false))

                    frequentActivitiesState.message?.let {
                        CaptionImage(
                            modifier = Modifier.size(150.dp),
                            image = painterResource(id = R.drawable.illustration_no_data),
                            caption = it
                        )
                    }
                }

                is UIState.Error -> {
                    onEvent(StatisticEvent.OnSwipeRefresh(false))

                    LaunchedEffect(snackbarHostState) {
                        frequentActivitiesState.message?.let {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}