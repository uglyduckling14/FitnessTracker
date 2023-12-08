package com.example.cs3200firebasestarter.ui.components.config

import androidx.compose.runtime.Immutable

/**
 * Configuration options for a line in a line chart.
 *
 * @property hasSmoothCurve Whether the line should have a smooth curve.
 * @property hasDotMarker Whether the line should have a dot marker at each data point.
 * @property strokeSize The size of the line stroke.
 */
@Immutable
data class LineConfig(
    val hasSmoothCurve: Boolean,
    val hasDotMarker: Boolean,
    val strokeSize: Float
)