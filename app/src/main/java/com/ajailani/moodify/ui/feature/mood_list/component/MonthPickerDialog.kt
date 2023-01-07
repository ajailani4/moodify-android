package com.ajailani.moodify.ui.feature.mood_list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.feature.mood_list.MoodListEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPickerDialog(
    onEvent: (MoodListEvent) -> Unit,
    monthMenuExpanded: Boolean,
    yearMenuExpanded: Boolean,
    selectedMonth: Int,
    selectedYear: Int
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(MoodListEvent.OnMonthPickerDialogVisChanged(false))
        },
        title = {
            Text(text = stringResource(id = R.string.select_month))
        },
        text = {
            Row {
                Box(modifier = Modifier.weight(3f)) {
                    OutlinedTextField(
                        modifier = Modifier.clickable {
                            onEvent(MoodListEvent.OnMonthMenuExpanded(true))
                        },
                        value = stringArrayResource(id = R.array.months)[selectedMonth],
                        onValueChange = {},
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ExpandMore,
                                contentDescription = "Expand more icon"
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    DropdownMenu(
                        modifier = Modifier.requiredSizeIn(maxHeight = 232.dp),
                        expanded = monthMenuExpanded,
                        onDismissRequest = {
                            onEvent(MoodListEvent.OnMonthMenuExpanded(false))
                        }
                    ) {
                        stringArrayResource(id = R.array.months).forEachIndexed { index, month ->
                            DropdownMenuItem(
                                text = { Text(text = month) },
                                onClick = {
                                    onEvent(MoodListEvent.OnMonthMenuExpanded(false))
                                    onEvent(
                                        MoodListEvent.OnSelectedMonthChanged(index)
                                    )
                                }
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(2f)) {
                    OutlinedTextField(
                        modifier = Modifier.clickable {
                            onEvent(MoodListEvent.OnYearMenuExpanded(true))
                        },
                        value = "$selectedYear",
                        onValueChange = {},
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ExpandMore,
                                contentDescription = "Expand more icon"
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    DropdownMenu(
                        modifier = Modifier.requiredSizeIn(maxHeight = 232.dp),
                        expanded = yearMenuExpanded,
                        onDismissRequest = {
                            onEvent(MoodListEvent.OnYearMenuExpanded(false))
                        }
                    ) {
                        (2020..2050).forEach { year ->
                            DropdownMenuItem(
                                text = { Text(text = "$year") },
                                onClick = {
                                    onEvent(MoodListEvent.OnYearMenuExpanded(false))
                                    onEvent(MoodListEvent.OnSelectedYearChanged(year))
                                }
                            )

                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onEvent(MoodListEvent.GetPagingMoods)
                onEvent(MoodListEvent.OnMonthPickerDialogVisChanged(false))
            }) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onEvent(MoodListEvent.OnMonthPickerDialogVisChanged(false))
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}