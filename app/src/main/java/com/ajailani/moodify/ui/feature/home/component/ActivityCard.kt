package com.ajailani.moodify.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.moodify.domain.model.Activity

@Composable
fun ActivityCard(
    activity: Activity
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        .data(activity.icon)
                        .build(),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                    contentDescription = "Activity icon"
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = activity.name,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}