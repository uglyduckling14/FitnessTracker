package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.cs3200firebasestarter.ui.theme.Purple80

@Composable
fun WorkoutListItem(
    name: String? = null,
    distance: Int? = null,
    exerciseType: Int? = null,
    onStartPressed: () -> Unit = {},
    isComplete: Boolean
){
    val EXERCISE_TYPES = arrayOf("Running", "Walking", "Hiking", "Biking", "Dancing")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Workout Name
            Text(
                text = name ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = Purple80
            )

            // Start Workout Icon
            if(!isComplete) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Purple80,
                    modifier = Modifier.clickable {
                        onStartPressed()
                    }
                )
            }
        }

        // Distance and Exercise Type
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Distance
            Text(
                text = (distance?.toString() + " meters"),
                style = MaterialTheme.typography.titleSmall
            )

            // Exercise Type
            Text(
                text = EXERCISE_TYPES[exerciseType!!],
                style = MaterialTheme.typography.titleSmall
            )

        }
        Divider()
    }
}