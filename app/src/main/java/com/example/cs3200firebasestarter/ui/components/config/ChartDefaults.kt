package com.example.cs3200firebasestarter.ui.components.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

object ChartDefaults {

    /**
     * Provides default values for the axis configuration.
     */
    fun axisConfigDefaults() = AxisConfig(
        showGridLabel = true,
        showAxes = true,
        showGridLines = true,
        axisStroke = 2F,
        axisColor = Color(0xFFD0CEBA),
        minLabelCount = 2
    )

    /**
     * Provides default colors for the chart.
     */
    fun colorDefaults() = ChartColors(
        contentColor = listOf(
            Color(0xFF1F2041),
            Color(0xFF4B3F72)
        ),
        backgroundColors = listOf(
            Color.Transparent, Color.Transparent
        )
    )

    /**
     * Provides default values for text label configuration in the chart.
     */
    fun defaultTextLabelConfig() = ChartyLabelTextConfig(
        textSize = TextUnit.Unspecified,
        fontStyle = null,
        fontWeight = null,
        fontFamily = null,
        textColor = Color(0xFFD0CEBA),
        maxLine = 1,
        overflow = TextOverflow.Ellipsis
    )
}