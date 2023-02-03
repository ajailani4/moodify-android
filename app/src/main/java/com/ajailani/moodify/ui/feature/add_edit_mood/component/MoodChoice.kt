package com.ajailani.moodify.ui.feature.add_edit_mood.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ajailani.moodify.R
import com.ajailani.moodify.ui.theme.*

@Composable
fun MoodChoice(
    mood: Int,
    moodName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            painter = painterResource(
                id = when (mood) {
                    1 -> {
                        if (isSelected) {
                            R.drawable.ic_filled_terrible_mood
                        } else {
                            R.drawable.ic_outlined_terrible_mood
                        }
                    }

                    2 -> {
                        if (isSelected) {
                            R.drawable.ic_filled_bad_mood
                        } else {
                            R.drawable.ic_outlined_bad_mood
                        }
                    }

                    3 -> {
                        if (isSelected) {
                            R.drawable.ic_filled_okay_mood
                        } else {
                            R.drawable.ic_outlined_okay_mood
                        }
                    }

                    4 -> {
                        if (isSelected) {
                            R.drawable.ic_filled_good_mood
                        } else {
                            R.drawable.ic_outlined_good_mood
                        }
                    }

                    5 -> {
                        if (isSelected) {
                            R.drawable.ic_filled_excellent_mood
                        } else {
                            R.drawable.ic_outlined_excellent_mood
                        }
                    }

                    else -> R.drawable.ic_filled_okay_mood
                }
            ),
            contentDescription = "Mood choice icon"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = moodName,
            color = when (mood) {
                1 -> TerribleMoodRed
                2 -> BadMoodOrange
                3 -> OkayMoodBlue
                4 -> GoodMoodGreen
                5 -> ExcellentMoodGreen
                else -> OkayMoodBlue
            },
            style = MaterialTheme.typography.labelLarge
        )
    }
}