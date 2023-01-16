package com.ajailani.moodify.ui.feature.statistic.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChartLegend(
    color: Color,
    text: String
) {
    Row {
        Box(
            modifier = Modifier
                .size(18.dp)
                .background(color = color, shape = CircleShape)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}