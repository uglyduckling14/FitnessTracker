package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.WorkoutListItem
import com.example.cs3200firebasestarter.ui.viewmodels.HomeViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true){
        viewModel.getWorkouts()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        LazyColumn{
            items(state.workouts, key = {it.id ?: ""}){ workout ->
                if(!workout.completed) {
                    WorkoutListItem(
                        name = workout.name,
                        distance = workout.distance,
                        exerciseType = workout.exerciseSession?.exerciseType,
                        onStartPressed = {
                            navHostController.navigate("startworkout?id=${workout.id}")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {context ->
                AdView(context).apply{
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            }
            )
    }
}