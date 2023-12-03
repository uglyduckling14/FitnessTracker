package com.example.cs3200firebasestarter.ui.models

import java.time.Instant

data class Workout(
    val startTime: Instant?=null,
    val endTime: Instant?=null,
    val caloriesBurnedRecord: Int?=null,
    val exerciseSession: ExerciseSessionRecord?=null,
    val distance: Int?=null,
    val userId:String?=null,
    val id:String?=null,
    val name:String?=null
) {
    constructor() : this(null, null,0, null, 0, "", "", "")
}