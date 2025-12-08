package com.androidace.canvascomposemaster.successanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.media.MediaPlayer
import android.util.Log
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
    scaleDuration: Int = 600,
    tickDuration: Int = 800,
    burstDuration: Int = 600,
    particleCount: Int = 20,
    enableSound: Boolean = true,
    soundResourceId: Int? = null // Optional sound resource from raw folder
) {
    val context = LocalContext.current
    var animationStarted by remember { mutableStateOf(false) }
    var tickAnimationStarted by remember { mutableStateOf(false) }
    var burstAnimationStarted by remember { mutableStateOf(false) }
    
    // Create sound effect function to load from raw folder
    fun playCustomSound() {
        if (enableSound && soundResourceId != null) {
            try {
                val mediaPlayer = MediaPlayer.create(context, soundResourceId)
                mediaPlayer.setVolume(0.8f, 0.8f)
                mediaPlayer?.apply {
                    setOnCompletionListener { release() }
                    start()
                }
            } catch (e: Exception) {
                // Silently handle sound errors
                e.printStackTrace()
            }
        }
    }
    
    // Circle scale animation
    val circleScale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(durationMillis = scaleDuration, easing = FastOutSlowInEasing),
        label = "circleScale"
    )
    
    // Tick progress animation
    val tickProgress by animateFloatAsState(
        targetValue = if (tickAnimationStarted) 1f else 0f,
        animationSpec = tween(durationMillis = tickDuration, easing = FastOutSlowInEasing),
        label = "tickProgress"
    )

    val particles = remember { mutableStateListOf<Particle>() }

    LaunchedEffect(Unit) {
        // Start circle scaling (silent)
        animationStarted = true
        
        // Wait for circle scaling to complete, then start tick
        delay(scaleDuration.toLong())
        tickAnimationStarted = true
        
        // Wait for tick animation to complete, then play success sound once
        delay(tickDuration.toLong())
        playCustomSound() // Success completion sound - optimal moment!
        delay(100) // Small delay before burst
        burstAnimationStarted = true

        // Generate particles starting from circle circumference
        repeat(particleCount) { index ->
            val angle = (2.0 * Math.PI * index) / particleCount // Evenly distributed angles
            particles.add(
                Particle(
                    angle = angle,
                    radius = Random.nextFloat() * 20f + 2f,
                    color = circleColor.copy(alpha = 0.8f),
                    alpha = Animatable(1f),
                    distanceMultiplier = Animatable(0f)
                )
            )
        }

        // Animate particles from circumference outward
        particles.forEach { particle ->
            launch {
                particle.distanceMultiplier.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = burstDuration, easing = FastOutSlowInEasing)
                )
            }
            launch {
                particle.alpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = burstDuration)
                )
            }
        }
        
        // Clean up particles after animation
        delay(burstDuration.toLong())
        particles.clear()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)// Slightly larger box to contain the burst
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            val sizePx = size.width
            val strokeWidth = 10.dp.toPx()
            val center = Offset(sizePx / 2f, sizePx / 2f)
            val mainCircleRadius = sizePx / 3f

            // 1. Draw the scaling circle
            if (circleScale > 0f) {
                drawCircle(
                    color = circleColor,
                    center = center,
                    radius = mainCircleRadius * circleScale,
                )
            }

            // 2. Define the tick mark path with proper 90-degree angle
            if (tickAnimationStarted) {
                val tickPath = Path().apply {
                    // Create a proper checkmark with 90-degree angle
                    val tickSize = mainCircleRadius * 0.7f
                    val startX = center.x - tickSize * 0.5f
                    val startY = center.y
                    val middleX = center.x - tickSize * 0.1f
                    val middleY = center.y + tickSize * 0.4f
                    val endX = center.x + tickSize * 0.6f
                    val endY = center.y - tickSize * 0.4f
                    
                    moveTo(startX, startY)
                    lineTo(middleX, middleY) // First stroke (down and right)
                    lineTo(endX, endY) // Second stroke (up and right, creating 90° angle)
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
                        cap = StrokeCap.Square,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(pathLength, pathLength),
                            phase = pathLength * (1f - tickProgress)
                        )
                    )
                )
            }

            // 4. Draw particles bursting from circumference
            particles.forEach { particle ->
                // Start particles from circle circumference and move outward
                val startDistance = mainCircleRadius
                val maxBurstDistance = sizePx * 0.8f
                val currentDistance = startDistance + (maxBurstDistance - startDistance) * particle.distanceMultiplier.value

                val x = center.x + currentDistance * cos(particle.angle).toFloat()
                val y = center.y + currentDistance * sin(particle.angle).toFloat()

                if (particle.alpha.value > 0f && burstAnimationStarted) {
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