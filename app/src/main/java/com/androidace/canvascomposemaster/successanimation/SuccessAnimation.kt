package com.androidace.canvascomposemaster.successanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.Files.size
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Data class to manage individual small circles (particles)
data class Particle(
    val id: Int = Random.nextInt(),
    // We only need the animation values now, position is calculated dynamically
    var angle: Double,
    var radius: Float,
    val color: Color,
    var alpha: Animatable<Float, *>,
    var distanceMultiplier: Animatable<Float, *>
)

@Composable
fun SuccessAnimationWithBurst(
    circleColor: Color = Color(0xFF4CAF50),
    tickColor: Color = Color.White,
    animationDuration: Int = 1000,
    burstDuration: Int = 800
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val tickProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration),
        label = "tickProgress"
    )

    val particles = remember { mutableStateListOf<Particle>() }

    LaunchedEffect(Unit) {
        animationPlayed = true
        // Trigger particle burst after the tick animation starts
        delay((animationDuration * 0.75).toLong())

        // Generate particles
        repeat(20) {
            particles.add(
                Particle(
                    angle = Random.nextDouble(0.0, 2.0 * Math.PI), // Random direction
                    radius = Random.nextFloat() * 4f + 2f,
                    color = circleColor,
                    alpha = Animatable(1f),
                    distanceMultiplier = Animatable(0f)
                )
            )
        }

        // Animate particles
        particles.forEach { particle ->
            // Use launch to run animations concurrently
            launch {
                particle.distanceMultiplier.animateTo(
                    targetValue = 1f, // Animates from center (0) to maximum distance (1)
                    animationSpec = tween(durationMillis = burstDuration)
                )
            }
            launch {
                particle.alpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = burstDuration)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .size(400.dp) // Slightly larger box to contain the burst
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            val sizePx = size.width
            val strokeWidth = 10.dp.toPx()
            val center = Offset(sizePx / 2f, sizePx / 2f)
            // The radius of the main green circle
            val mainCircleRadius = sizePx / 3f

            // 1. Draw the surrounding circle
            drawCircle(
                color = circleColor,
                center = center,
                radius = mainCircleRadius,
            )

            // 2. Define the tick mark path (centered within the main circle)
            val tickPath = Path().apply {
                // Coordinates adjusted to fit neatly inside the mainCircleRadius
                moveTo(center.x - mainCircleRadius * 0.3f, center.y)
                lineTo(center.x - mainCircleRadius * 0.1f, center.y + mainCircleRadius * 0.25f)
                lineTo(center.x + mainCircleRadius * 0.3f, center.y - mainCircleRadius * 0.3f)
            }

            // 3. Animate the tick path drawing
            val pathMeasure = androidx.compose.ui.graphics.PathMeasure()
            pathMeasure.setPath(tickPath, false)
            val pathLength = pathMeasure.length

            drawPath(
                path = tickPath,
                color = tickColor,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(pathLength, pathLength),
                        phase = pathLength * (1f - tickProgress)
                    )
                )
            )

            // 4. Draw expanding particles originating from the center
            particles.forEach { particle ->
                // Animate distance from center. Max distance goes beyond the main circle radius
                val maxBurstDistance = sizePx * 1.5f
                val currentDistance = maxBurstDistance * particle.distanceMultiplier.value

                val x = center.x + currentDistance * cos(particle.angle).toFloat()
                val y = center.y + currentDistance * sin(particle.angle).toFloat()

                if (particle.alpha.value > 0f) {
                    drawCircle(
                        color = particle.color.copy(alpha = particle.alpha.value),
                        center = Offset(x, y),
                        radius = particle.radius
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessAnimationWithBurst() {
    Column(modifier = Modifier.fillMaxSize()) {
        SuccessAnimationWithBurst()
    }
}