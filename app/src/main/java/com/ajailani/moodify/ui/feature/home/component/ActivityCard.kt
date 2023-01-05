package com.ajailani.moodify.ui.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.moodify.R
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
            Surface(
                modifier = Modifier.size(30.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                AsyncImage(
                    modifier = Modifier.size(20.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(activity.icon)
                        .build(),
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