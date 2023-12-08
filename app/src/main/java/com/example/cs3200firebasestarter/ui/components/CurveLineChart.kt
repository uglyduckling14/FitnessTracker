package com.example.cs3200firebasestarter.ui.components

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cs3200firebasestarter.ui.components.config.AxisConfig
import com.example.cs3200firebasestarter.ui.components.config.ChartDefaults
import com.example.cs3200firebasestarter.ui.components.config.CurvedLineChartColors
import com.example.cs3200firebasestarter.ui.components.config.CurvedLineChartDefaults
import com.example.cs3200firebasestarter.ui.components.config.LineChartDefaults
import com.example.cs3200firebasestarter.ui.components.config.LineConfig
import com.example.cs3200firebasestarter.ui.components.config.chartDataToOffset


//Tried to import dependencies but Android was not having it so copied implementation from Git
@Composable
fun CurveLineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    chartColors: CurvedLineChartColors = CurvedLineChartDefaults.defaultColor(),
) {
    val points = dataCollection.data

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }
    var pointBound by remember { mutableStateOf(0F) }

    val horizontalScale = chartWidth.div(points.count())
    val verticalScale = chartHeight.div((dataCollection.maxYValue() - dataCollection.minYValue()))

    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier,
        axisConfig = axisConfig
    ) {
        val contentColor = Brush.linearGradient(chartColors.contentColor)
        val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)
        val dotColor = Brush.linearGradient(chartColors.dotColor)

        val minYValue = dataCollection.minYValue()

        Canvas(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxSize()
                .onSizeChanged { size ->
                    chartWidth = size.width.toFloat()
                    chartHeight = size.height.toFloat()
                    pointBound = size.width.div(
                        points
                            .count()
                            .times(1.2F)
                    )
                }
                .drawBehind {
                    if (axisConfig.showAxes) {
                        drawYAxis(axisConfig.axisColor, axisConfig.axisStroke)
                        drawXAxis(axisConfig.axisColor, axisConfig.axisStroke)
                    }
                    if (axisConfig.showGridLines) {
                        drawGridLines(chartWidth, chartHeight, padding.toPx())
                    }
                }
        ) {
            val graphPathPoints = mutableListOf<PointF>()
            val radius = size.width * radiusScale

            Path().apply {
                moveTo(0f, size.height) // Start from the bottom-left corner of the canvas

                val firstData = dataCollection.data.first()
                val initialX = 0f
                val initialY = size.height - ((firstData.yValue - minYValue) * verticalScale)
                lineTo(initialX, initialY) // Move to the initial point

                dataCollection.data.fastForEachIndexed { index, data ->
                    val centerOffset = chartDataToOffset(
                        index,
                        pointBound,
                        size,
                        data.yValue,
                        horizontalScale,
                    )

                    val x = centerOffset.x
                    val y = size.height - ((data.yValue - minYValue) * verticalScale)
                    val innerX =
                        x.coerceIn(centerOffset.x - radius / 2, centerOffset.x + radius / 2)
                    val innerY = y.coerceIn(radius, size.height - radius)

                    graphPathPoints.add(PointF(innerX, innerY))

                    if (index > 0) {
                        val prevIndex = index.minus(1)
                        val previousCenterOffset = chartDataToOffset(
                            index.minus(1),
                            pointBound,
                            size,
                            data.yValue,
                            horizontalScale,
                        )
                        val prevX = previousCenterOffset.x
                        val prevY =
                            size.height - (
                                    dataCollection.data[prevIndex].yValue -
                                            dataCollection.minYValue()
                                    ) * verticalScale

                        val prevInnerX = prevX.coerceIn(
                            prevX - radiusScale * size.width,
                            prevX + radiusScale * size.width
                        )
                        val prevInnerY = prevY.coerceIn(0f, size.height)

                        cubicTo(
                            prevInnerX + (innerX - prevInnerX) / 2, prevInnerY,
                            prevInnerX + (innerX - prevInnerX) / 2, innerY,
                            innerX, innerY
                        )
                    }
                    if (points.count() < 14) {
                        drawXAxisLabels(
                            data = data.xValue,
                            center = centerOffset,
                            count = points.count(),
                            padding = padding.toPx(),
                            minLabelCount = axisConfig.minLabelCount,
                        )
                    }
                }
                // Close the path
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()

                // Draw the background path
                drawPath(
                    path = this,
                    brush = contentColor
                )
            }
            if (lineConfig.hasDotMarker) {
                graphPathPoints.fastForEach { point ->
                    drawCircle(
                        brush = dotColor,
                        radius = radiusScale * size.width,
                        center = Offset(point.x, point.y)
                    )
                }
            }
        }
    }
}