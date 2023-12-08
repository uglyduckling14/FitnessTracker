package com.example.cs3200firebasestarter.ui.models

import androidx.compose.runtime.Immutable
import com.example.cs3200firebasestarter.ui.components.ChartData

/**
 * Represents a data point for a line chart.
 *
 * @property yValue The y-axis value of the data point.
 * @property xValue The x-axis value of the data point.
 */
@Immutable
data class LineData(override val yValue: Float, override val xValue: Any) : ChartData {
    constructor(): this(0F, 0 )
    override val chartString: String
        get() = "Line Chart"
}