package com.example.cs3200firebasestarter.ui

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cs3200firebasestarter.ui.navigation.RootNavigation
import com.example.cs3200firebasestarter.ui.theme.CS3200FirebaseStarterTheme

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun App(context:Context) {
    CS3200FirebaseStarterTheme {
        var permissionState by remember { mutableStateOf("requesting permission") }
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){ granted ->
            if(granted) permissionState = "Permission was granted"
            else permissionState = "Permission was denied"
        }
        LaunchedEffect(true){
            launcher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
        RootNavigation(context)
    }

}