package com.ajailani.moodify.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.util.Formatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodCard(
    moodItem: MoodItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                painter = painterResource(
                    id = when (moodItem.mood) {
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
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Column {
                    Text(
                        text = stringResource(
                            id = when (moodItem.mood) {
                                1 -> R.string.terrible
                                2 -> R.string.bad
                                3 -> R.string.okay
                                4 -> R.string.good
                                5 -> R.string.excellent
                                else -> R.string.okay
                            }
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = moodItem.activity,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "${Formatter.formatDate(moodItem.date)}  •  ${moodItem.time}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}