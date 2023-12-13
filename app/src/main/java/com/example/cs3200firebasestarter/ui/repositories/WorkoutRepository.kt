package com.example.cs3200firebasestarter.ui.repositories

import com.example.cs3200firebasestarter.ui.models.ExerciseSessionRecord
import com.example.cs3200firebasestarter.ui.models.LineData
import com.example.cs3200firebasestarter.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object WorkoutRepository{

    private val workoutCache = mutableListOf<Workout>()
    private var cacheInitialized = false

    suspend fun getWorkouts(): List<Workout>{
        if(!cacheInitialized){
            val snapshot = Firebase.firestore.collection("workoutRecords")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            workoutCache.clear()
            workoutCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }
        return workoutCache
    }

    suspend fun createWorkout(
        startTime: Int,
        endTime:Int,
        caloriesBurnedRecord: Int,
        distance:Int,
        exerciseSession: ExerciseSessionRecord,
        name:String,
        completed:Boolean
    ):Workout{
        val doc = Firebase.firestore.collection("workoutRecords").document()
        val workout = Workout(
            startTime = startTime,
            endTime = endTime,
            distance = distance,
            caloriesBurnedRecord = caloriesBurnedRecord,
            exerciseSession = exerciseSession,
            name = name,
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            completed = completed
        )
        doc.set(workout).await()
        workoutCache.add(workout)
        return workout
    }
    suspend fun updateWorkout(workout: Workout){
        Firebase.firestore
            .collection("workoutRecords")
            .document(workout.id!!)
            .set(workout)
            .await()

        val oldWorkout = workoutCache.indexOfFirst {
            it.id == workout.id
        }
        workoutCache[oldWorkout] = workout
    }
}