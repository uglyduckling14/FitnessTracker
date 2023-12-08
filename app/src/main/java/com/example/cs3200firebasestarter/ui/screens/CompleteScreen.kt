package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs3200firebasestarter.ui.components.ChartDataCollection
import com.example.cs3200firebasestarter.ui.components.CurveLineChart
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.viewmodels.WorkoutViewModel

@Composable
fun CompleteScreen(navHostController: NavHostController, id:String) {
    val viewModel: WorkoutViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true){
        viewModel.setUpInitialState(id)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        Text(text = "Congrats you completed your workout (${state.exerciseTitle}! \n" +
                "You burned ${state.caloriesBurned} calories over ${
                    if(state.endTime != "" && state.startTime !=""){
                        state.endTime.toInt() - state.startTime.toInt()
                    } else {
                        "0"
                    }
                } minutes")
        if(state.lineData.isNotEmpty()) {
            CurveLineChart(
                dataCollection = ChartDataCollection(state.lineData),
                modifier = Modifier
                    .size(450.dp),
            )
        }
        Button(onClick = { navHostController.navigate(Routes.home.route) }) {
            Text(text= "Return to home page")
        }
    }
}