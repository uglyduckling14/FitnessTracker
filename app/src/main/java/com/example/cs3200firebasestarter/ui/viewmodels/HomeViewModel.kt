package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.models.Workout
import com.example.cs3200firebasestarter.ui.repositories.WorkoutRepository

class HomeScreenState{
    val _workouts = mutableListOf<Workout>()
    val workouts: List<Workout> get() = _workouts
}
class HomeViewModel(application: Application): AndroidViewModel(application) {
    val uiState = HomeScreenState()
    suspend fun getWorkouts(){
        val workouts = WorkoutRepository.getWorkouts()
        uiState._workouts.clear()
        uiState._workouts.addAll(workouts)
    }
}