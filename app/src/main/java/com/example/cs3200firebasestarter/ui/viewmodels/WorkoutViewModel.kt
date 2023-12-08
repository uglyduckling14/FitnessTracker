package com.example.cs3200firebasestarter.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.cs3200firebasestarter.ui.repositories.WorkoutRepository
import com.example.cs3200firebasestarter.ui.models.ExerciseSessionRecord
import com.example.cs3200firebasestarter.ui.models.LineData


class WorkoutScreenState{
    var error by mutableStateOf(false)
    var distance by mutableStateOf(0)
    var caloriesBurned by mutableStateOf(0)
    var startTime by mutableStateOf("")
    var endTime by mutableStateOf("")
    var exerciseTitle by mutableStateOf("")
    var exerciseType by mutableStateOf(0)
    var exerciseNotes by mutableStateOf("")
    var completed by mutableStateOf(false)
    var lineData by mutableStateOf<List<LineData>>(emptyList())
}


class WorkoutViewModel(application: Application):
        AndroidViewModel(application){

    val uiState = WorkoutScreenState()
            var id:String? = null
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
                    val eRecord = ExerciseSessionRecord(
                        uiState.exerciseNotes,
                        uiState.exerciseType,
                        uiState.exerciseTitle,
                        uiState.lineData
                    )
                    WorkoutRepository.createWorkout(
                        0,
                        0,
                        uiState.caloriesBurned,
                        uiState.distance,
                        eRecord,
                        uiState.exerciseTitle
                    )
                }else{ // update
                    val workout = WorkoutRepository.getWorkouts().find{it.id == id}
                    if(workout !=null){
                        WorkoutRepository.updateWorkout(
                            workout.copy(
                                startTime = uiState.startTime.toInt(),
                                endTime = uiState.endTime.toInt(),
                                caloriesBurnedRecord = uiState.caloriesBurned,
                                exerciseSession = ExerciseSessionRecord(
                                    title = uiState.exerciseTitle,
                                    exerciseType = uiState.exerciseType,
                                    notes = uiState.exerciseNotes,
                                    dataPoints = uiState.lineData
                                ),
                                distance = uiState.distance,
                                name = uiState.exerciseTitle,
                                completed = uiState.completed
                            )
                        )
                        setUpInitialState(id)
                    }
                }

            }
        }
