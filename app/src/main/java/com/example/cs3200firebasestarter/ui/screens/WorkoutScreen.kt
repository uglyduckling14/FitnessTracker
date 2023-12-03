package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextAlign
import com.example.cs3200firebasestarter.ui.viewmodels.WorkoutViewModel
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun WorkoutScreen(navHostController: NavHostController) {

    val viewModel: WorkoutViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround) {
        Surface(shadowElevation = 2.dp) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Title", style = MaterialTheme.typography.headlineSmall)
                FormField(
                    value = state.exerciseTitle,
                    onValueChange = { state.exerciseTitle = it },
                    placeholder = { Text("Title") },
                    error = state.error
                )
                FormField(
                    value = state.distance.toString(),
                    onValueChange = {
                        if(it.isNotBlank()) {
                            state.distance = it.trim().toInt()
                        }
                    },
                    placeholder = { Text("Distance") },
                    error = state.error
                )
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = { scope.launch {
                        viewModel.saveWorkout()
                        navHostController.navigate(Routes.home.route)
//                        if (CharacterRepository.getCurrentUserId() != null) {
//                            navHostController.navigate(Routes.appNavigation.route) {
//                                popUpTo(navHostController.graph.id) {
//                                    inclusive = true
//                                }
//                            }
//                        }
                    } }, elevation = null) {
                        Text(text = "Save")
                    }
                }
                Text(
                    text = "this is where the error message will go",
                    style = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}
