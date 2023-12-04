package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.repositories.WorkoutRepository
import java.time.ZonedDateTime
import com.example.cs3200firebasestarter.ui.models.ExerciseSessionRecord


class WorkoutScreenState{
    var error by mutableStateOf(false)
    var distance by mutableStateOf(0)
    var caloriesBurned by mutableStateOf(0)
    var startTime by mutableStateOf("")
    var endTime by mutableStateOf("")
    var exerciseTitle by mutableStateOf("")
    var exerciseType by mutableStateOf(0)
    var exerciseNotes by mutableStateOf("")
}

class WorkoutViewModel(application: Application):
        AndroidViewModel(application){

    val uiState = WorkoutScreenState()
            var id:String? = null
            val zoneOffset = System.currentTimeMillis().toInt();
            suspend fun setUpInitialState(id: String?){
                if(id == null || id == "new") return
                this.id = id
                val workout = WorkoutRepository.getWorkouts().find{it.id == id} ?: return

                uiState.caloriesBurned = workout.caloriesBurnedRecord!!
                uiState.distance = workout.distance!!
                uiState.startTime = workout.startTime.toString()
                uiState.endTime = workout.endTime.toString()
                uiState.exerciseTitle = workout.exerciseSession?.title.toString()
                uiState.exerciseType = workout.exerciseSession?.exerciseType!!
                uiState.exerciseNotes = workout.exerciseSession.notes.toString()
            }

            suspend fun saveWorkout(){
                //validate()
                if(uiState.error){
                    return
                }
                if(id == null){
                    val off = System.currentTimeMillis().toInt();
                    val eRecord = ExerciseSessionRecord(
                        uiState.exerciseNotes,
                        uiState.exerciseType,
                        uiState.exerciseTitle
                    )
                    WorkoutRepository.createWorkout(
                        zoneOffset,
                        off,
                        uiState.caloriesBurned,
                        uiState.distance,
                        eRecord,
                        uiState.exerciseTitle
                    )
                }
            }
        }
