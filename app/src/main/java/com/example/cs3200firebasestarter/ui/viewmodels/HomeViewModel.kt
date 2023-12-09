package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs3200firebasestarter.ui.models.Workout
import com.example.cs3200firebasestarter.ui.repositories.WorkoutRepository
import kotlinx.coroutines.launch

class HomeScreenState{
    val _workouts = mutableStateOf<List<Workout>>(emptyList())
    val workouts: List<Workout> get() = _workouts.value
}
class HomeViewModel(application: Application): AndroidViewModel(application) {
    val uiState = HomeScreenState()
    suspend fun getWorkouts(){
        viewModelScope.launch {
            val workouts = WorkoutRepository.getWorkouts()
            uiState._workouts.value = emptyList()
            uiState._workouts.value = workouts
        }
    }
}