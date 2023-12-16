package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.geometry.Size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.toSize
import com.example.cs3200firebasestarter.ui.viewmodels.WorkoutViewModel
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.theme.Purple80
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(navHostController: NavHostController) {

    val viewModel: WorkoutViewModel = viewModel()
    val state = viewModel.uiState
    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    LaunchedEffect(true) {
        viewModel.setUpInitialState("new")
    }
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val EXERCISE_TYPES = arrayOf("Running", "Walking", "Hiking", "Biking", "Dancing")

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
                OutlinedTextField(
                    value = EXERCISE_TYPES[state.exerciseType],
                    onValueChange = {state.exerciseType = it.toInt()},
                    label = { Text( "Exercise Type")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            textfieldSize = coordinates.size.toSize()
                        },
                    trailingIcon = {
                        Icon(icon,"contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false},
                    modifier = Modifier.width(with(LocalDensity.current){textfieldSize.width.toDp()})) {
                    EXERCISE_TYPES.forEach {label ->
                        DropdownMenuItem(
                            text = { Text(text = label, color = Color.White) },
                            onClick = {
                                state.exerciseType = EXERCISE_TYPES.lastIndexOf(label)
                                expanded = false
                            }
                        )
                    }
                }
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel", color = Purple80)
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(Purple80),
                        onClick = { scope.launch {
                            viewModel.saveWorkout()
                            navHostController.navigate(Routes.home.route)
                    } }, elevation = null) {
                        Text(text = "Save")
                    }
                }
                if(state.error) {
                    Text(
                        text = "Please enter a title",
                        style = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }
    }
}
