package com.example.cs3200firebasestarter.ui.components.config

import androidx.compose.ui.graphics.Color
import com.example.cs3200firebasestarter.ui.theme.Blue40
import com.example.cs3200firebasestarter.ui.theme.Blue80
import com.example.cs3200firebasestarter.ui.theme.Purple40
import com.example.cs3200firebasestarter.ui.theme.Purple80

/**
 * Default configuration values for a curved line chart.
 */
object CurvedLineChartDefaults {

    /**
     * Returns the default colors for the curved line chart.
     *
     * @return The default colors for the curved line chart.
     */
    fun defaultColor() = CurvedLineChartColors(
        contentColor = listOf(
            Blue80,
            Purple40
        ),
        dotColor = listOf(
            Blue40,
            Purple80,
        ),
        backgroundColors = listOf(
            Color.White,
            Color.White,
        ),
    )
}