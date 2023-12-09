package com.example.cs3200firebasestarter.ui.screens

import android.content.ClipData
import androidx.compose.runtime.rememberCoroutineScope
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.ChartDataCollection
import com.example.cs3200firebasestarter.ui.components.CurveLineChart
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.models.LineData
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.repositories.WorkoutRepository
import com.example.cs3200firebasestarter.ui.viewmodels.WorkoutViewModel
import kotlinx.coroutines.launch

@Composable
fun StartScreen(navHostController: NavHostController, id: String, context: Context){
    val viewModel: WorkoutViewModel = viewModel()
    val state = viewModel.uiState
    var initialSteps = 0;
    var getInit = true;
    val scope = rememberCoroutineScope()

    LaunchedEffect(true){
        viewModel.setUpInitialState(id)
        state.startTime = (System.currentTimeMillis()/60000.00).toInt().toString()
    }
    var stepOutput by remember {
        mutableStateOf("")
    }
    val handler = android.os.Handler(Looper.getMainLooper())
    var isUpdating = false

    DisposableEffect(true) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        val stepListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (!isUpdating) {
                    isUpdating = true
                    if(getInit){
                        initialSteps = event.values[0].toInt()
                        getInit = false
                    }

                    handler.post {
                        stepOutput = (event.values[0]- initialSteps).toString()
                        calculateCalories()
                        calculateDistance()
                        state.lineData = state.lineData + LineData(System.currentTimeMillis().toInt()/60000F, state.caloriesBurned)
                        isUpdating = false
                    }
                }
            }
            fun calculateCalories(){
                when(state.exerciseType){
                    0 -> {state.caloriesBurned = (0.08 * stepOutput.toDouble()).toInt() }
                    1 -> {state.caloriesBurned = (0.04 * stepOutput.toDouble()).toInt() }
                    2 -> {state.caloriesBurned = (0.06 * stepOutput.toDouble()).toInt() }
                    3 -> {state.caloriesBurned = (0.12 * stepOutput.toDouble()).toInt() }
                    4 -> {state.caloriesBurned = (0.10 * stepOutput.toDouble()).toInt() }
                }
            }

            fun calculateDistance(){
                when(state.exerciseType){
                    0 -> {state.distance = (0.004 * stepOutput.toDouble()).toInt() }
                    1 -> {state.distance = (0.002 * stepOutput.toDouble()).toInt() }
                    2 -> {state.distance = (0.006 * stepOutput.toDouble()).toInt() }
                    3 -> {state.distance = (0.012 * stepOutput.toDouble()).toInt() }
                    4 -> {state.distance = (0.004 * stepOutput.toDouble()).toInt() }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                print(accuracy)
            }

        }
        sensorManager.registerListener(stepListener, stepSensor, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(stepListener)
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround) {
        Text(text = state.exerciseTitle)
        Text(text = stepOutput)
        Text(text = state.caloriesBurned.toString())
        Text(text = state.distance.toString())
        FormField(
            value = state.exerciseNotes,
            onValueChange = {
                state.exerciseNotes = it},
            placeholder = {Text("Notes")})
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable{
                state.completed = true
                state.endTime = (System.currentTimeMillis()/60000.00).toInt().toString()
                scope.launch{
                    viewModel.saveWorkout()
                    navHostController.navigate("completeworkout?id=${id}")
                }
            }
        )
    }
}