package com.example.cs3200firebasestarter.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Looper
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.getSystemService
import com.example.cs3200firebasestarter.ui.navigation.RootNavigation
import com.example.cs3200firebasestarter.ui.theme.CS3200FirebaseStarterTheme
import java.util.logging.Handler

@Composable
fun App(context:Context) {
    var stepOutput =""
    CS3200FirebaseStarterTheme {
        val handler = android.os.Handler(Looper.getMainLooper())
        var isUpdating = false

        DisposableEffect(true) {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

            val stepListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    if (!isUpdating) {
                        isUpdating = true

                        handler.post {
                            stepOutput = event.values[0].toString()
                            isUpdating = false
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

                }

            }
            sensorManager.registerListener(stepListener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
            onDispose {
                sensorManager.unregisterListener(stepListener)
            }
        }
        RootNavigation(context)
    }

}
fun convertSensorReadingToString(reading:FloatArray): String{
    return reading.joinToString { "\n" }
}