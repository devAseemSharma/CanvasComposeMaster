package com.androidace.canvascomposemaster.clock

import android.icu.util.Calendar
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ComposeClock(
    clockLines: Int = 60,
    clockStyle: ClockStyle = ClockStyle(),
    currentTime: Calendar,
    modifier: Modifier = Modifier
) {
    val radius = clockStyle.radius
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    var angle by remember {
        mutableStateOf(0f)
    }


    Canvas(modifier = modifier) {

        center = this.center


        val canvasWidth = size.width
        val canvasHeight = size.height

        // Calculate the initial center of the canvas
        val initialCenterX = canvasWidth / 2f
        val initialCenterY = canvasHeight / 2f


        // Calculate the new center with offsets
        val newCenterX = initialCenterX
        val newCenterY = initialCenterY




        circleCenter = Offset(newCenterX, newCenterY + 100.dp.toPx())

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                clockStyle.radius.toPx(),
                android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    style = android.graphics.Paint.Style.FILL
                    setShadowLayer(
                        60f,
                        0f, 0f, android.graphics.Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        for (i in 0..clockLines) {
            val angle = (i * 6f)
            val angleInRad = (angle - 90) * ((PI / 180f).toFloat())
            val lineType = when {
                i % 5 == 0 -> ClockLines.HourLines
                i % 4 == 0 -> ClockLines.MinuteLines
                else -> {
                    ClockLines.MinuteLines
                }
            }

            val lineLength = when (lineType) {
                ClockLines.MinuteLines -> clockStyle.minuteLineLength.toPx()
                ClockLines.HourLines -> clockStyle.hourLineLength.toPx()
            }

            val lineColor = when (lineType) {
                ClockLines.MinuteLines -> clockStyle.minuteLineColor
                ClockLines.HourLines -> clockStyle.hourLineColor
            }

            val lineStart = Offset(
                x = (radius.toPx() - lineLength) * cos(angleInRad) + circleCenter.x,
                y = (radius.toPx() - lineLength) * sin(angleInRad) + circleCenter.y
            )

            val lineEnd = Offset(
                x = radius.toPx() * cos(angleInRad) + circleCenter.x,
                y = radius.toPx() * sin(angleInRad) + circleCenter.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
        }

        // Call the implemented hands function
        clockHands(circleCenter, radius.toPx(), currentTime)
    }
}

/**
 * Draws the hour, minute, and second hands of the clock.
 *
 * @param center The center offset of the canvas.
 * @param radius The maximum radius of the clock face.
 * @param time The current time to display.
 */

fun DrawScope.clockHands(center: Offset, radius: Float, time: Calendar) {
    val hours = time.get(Calendar.HOUR)
    val minutes = time.get(Calendar.MINUTE)
    val seconds = time.get(Calendar.SECOND)

    // Angle calculations:
    // 6 degrees per minute/second (360/60)
    // 30 degrees per hour (360/12)
    // The hour hand also moves based on minutes elapsed.

    val secondsAngle = seconds * 6f
    val minutesAngle = minutes * 6f + secondsAngle / 60f // Smooth minute movement
    val hoursAngle = (hours % 12 + minutes / 60f) * 30f

    // Draw Second Hand (Red, thin)
    rotate(degrees = secondsAngle - 90f, pivot = center) {
        drawLine(
            color = Color.Red,
            start = center,
            end = Offset(center.x + radius * 0.9f, center.y),
            strokeWidth = 2f,
            cap = StrokeCap.Round
        )
    }

    // Draw Minute Hand (Blue, medium)
    rotate(degrees = minutesAngle - 90f, pivot = center) {
        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(center.x + radius * 0.8f, center.y),
            strokeWidth = 6f,
            cap = StrokeCap.Round
        )
    }

    // Draw Hour Hand (Black, thick)
    rotate(degrees = hoursAngle - 90f, pivot = center) {
        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(center.x + radius * 0.5f, center.y),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }

    // Draw center pin
    drawCircle(color = Color.DarkGray, radius = 8f, center = center)
}