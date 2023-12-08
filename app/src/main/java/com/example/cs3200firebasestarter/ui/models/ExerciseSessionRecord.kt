package com.example.cs3200firebasestarter.ui.models

data class ExerciseSessionRecord(
    val title: String?=null,
    val exerciseType: Int?=null,
    val notes: String?=null,
    val dataPoints: List<LineData>
){
    constructor(): this("", null, "", emptyList())
}