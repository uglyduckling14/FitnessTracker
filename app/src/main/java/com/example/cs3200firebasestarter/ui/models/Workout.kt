package com.example.cs3200firebasestarter.ui.models


data class Workout(
    val startTime: Int,
    val endTime: Int,
    val caloriesBurnedRecord: Int?=null,
    val exerciseSession: ExerciseSessionRecord?=null,
    val distance: Int?=null,
    val name:String?=null,
    val completed:Boolean=false,
    val userId:String?=null,
    val id:String?=null,
) {
    constructor() : this(0, 0,0, null, 0,  "", false, "", "")
}