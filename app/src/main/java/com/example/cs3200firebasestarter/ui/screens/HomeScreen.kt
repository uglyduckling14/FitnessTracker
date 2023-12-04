package com.example.cs3200firebasestarter.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.WorkoutListItem
import com.example.cs3200firebasestarter.ui.viewmodels.HomeViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun HomeScreen(navHostController: NavHostController,context: Context) {
    val viewModel: HomeViewModel = viewModel()
    val state = viewModel.uiState
    var stepOutput by remember {
        mutableStateOf("")
    }

    DisposableEffect(true){
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val stepListener = object: SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                stepOutput = convertSensorReadingToString(event.values)
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

            }

        }
        sensorManager.registerListener(stepListener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(stepListener)
        }
    }
    Text(text = stepOutput)
    LaunchedEffect(true){
        viewModel.getWorkouts()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        LazyColumn{
            items(state.workouts, key = {it.id ?: ""}){ workout ->
                WorkoutListItem(
                    name = workout.name,
                    distance = workout.distance,
                    exerciseType = workout.exerciseSession?.exerciseType,
                    onEditPressed = {navHostController.navigate("editworkout?id=${workout.id}")}
                )
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
fun convertSensorReadingToString(reading:FloatArray): String{
    return reading.joinToString { "\n" }
}