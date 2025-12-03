package com.androidace.canvascomposemaster

import android.R
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.buildSpannedString
import com.androidace.canvascomposemaster.clock.ComposeClock
import com.androidace.canvascomposemaster.paths.DrawTextOnPath
import com.androidace.canvascomposemaster.ui.theme.CanvasComposeMasterTheme
import com.androidace.canvascomposemaster.ui.theme.DarkGreen
import com.androidace.canvascomposemaster.weightpicker.Scale
import com.androidace.canvascomposemaster.weightpicker.ScaleStyle
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasComposeMasterTheme {
                CanvasPathBasic()
            }
        }
    }
}

@Composable
private fun CanvasPathBasic() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(1000f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            //quadraticBezierTo(x1 = 800f, y1 = 300f, x2 = 500f, y2 = 100f)
            cubicTo(800f, 500f, 800f, 100f, 500f, 100f)
            //close()
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 10.dp.toPx(),
                cap = StrokeCap.Round, // Makes the end of the line as rounded
                join = StrokeJoin.Round, // Makes the joint of the stroke as rounded or whatever value added
                miter = 5f // Control how much we want to cutoff when angle is sharper
            )
        )
    }
}

@Composable
private fun PathOperations() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squaredWithoutOp = Path().apply {
            addRect(Rect(Offset(200f, 200f), Size(200f, 200f)))
        }

        val circle = Path().apply {
            addOval(Rect(Offset(200f, 200f), 100f))
        }

        val pathWithOp = Path().apply {
            op(squaredWithoutOp, circle, PathOperation.Xor)
        }

        drawPath(
            path = squaredWithoutOp,
            color = Color.Red,
            style = Stroke(width = 2.dp.toPx())
        )

        drawPath(
            path = circle,
            color = Color.Blue,
            style = Stroke(width = 2.dp.toPx())
        )

        drawPath(
            path = pathWithOp,
            color = Color.Green,
            style = Fill
        )
    }
}

@Composable
fun PathAnimation(modifier: Modifier = Modifier) {
    val pathPortion = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 5000
            )
        )
    }
    val path = Path().apply {
        moveTo(100f, 100f)
        quadraticBezierTo(400f, 400f, 100f, 400f)
    }
    /*val outPath = Path()
    PathMeasure().apply {
        setPath(path, false)
        getSegment(0f, pathPortion.value * length, outPath)
    }*/
    val outPath = android.graphics.Path()
    val pos = FloatArray(2)
    val tan = FloatArray(2)
    android.graphics.PathMeasure().apply {
        setPath(path.asAndroidPath(), false)
        getSegment(0f, pathPortion.value * length, outPath, true)
        getPosTan(pathPortion.value * length, pos, tan)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPath(
            path = outPath.asComposePath(),
            color = Color.Red,
            style = Stroke(width = 5.dp.toPx())
        )
        val x = pos[0]
        val y = pos[1]
        val degrees = -atan2(tan[0], tan[1]) * (180f / PI.toFloat()) - 180f
        rotate(degrees, pivot = Offset(x, y)) {
            drawPath(
                path = Path().apply {
                    moveTo(x, y - 30f)
                    lineTo(x - 30f, y + 60f)
                    lineTo(x + 30f, y + 60f)
                    close()
                },
                color = Color.Red
            )
        }
    }
}

/**
 * Clipping allows to convert to other shape and change the bounds of one shape when
 * clipped to another
 * **/

@Composable
fun ClipPathSample(modifier: Modifier = Modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val circle = Path().apply {
            addOval(Rect(center = Offset(400f, 400f), radius = 300f))
        }

        drawPath(
            path = circle,
            color = Color.Black,
            style = Stroke(width = 5.dp.toPx())
        )

        clipPath(
            path = circle
        ) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(400f, 400f),
                size = Size(400f, 400f)
            )
        }
    }
}

/**
 *    val phase by infiniteTransition.animateFloat(
 *         initialValue = 0f,
 *         targetValue = 10000f,
 *         animationSpec = InfiniteRepeatableSpec(
 *             animation = tween(60000, easing = LinearEasing)
 *         )
 *     )
 *           drawPath(
 *               path = path,
 *               color = Color.Red,
 *               style = Stroke(
 *                   width = 5.dp.toPx(),
 *                   pathEffect = PathEffect.dashPathEffect(
 *                       intervals = floatArrayOf(50f, 30f),
 *                       phase = phase
 *                   )
 *               )
 *           )

 *    To make corners rounded without complicated calculations
 *
 *    @sample
 *    drawPath(
 *          path = path,
 *          color = Color.Red,
 *          style = Stroke(
 *              width = 5.dp.toPx(),
 *              pathEffect = PathEffect.cornerPathEffect(
 *                  radius = 1000f
 *              )
 *          )
 *      )
 *    @sample
 *
 *    Stamped Path Effect
 *
 *    val oval = Path().apply {
 *         addOval(Rect(topLeft = Offset.Zero, bottomRight = Offset(40f, 10f)))
 *    }
 *
 *    drawPath(
 *             path = path,
 *             color = Color.Red,
 *             style = Stroke(
 *                 width = 5.dp.toPx(),
 *                 pathEffect = PathEffect.stampedPathEffect(
 *                     shape = oval,
 *                     advance = 100f,
 *                     phase = phase,
 *                     style = StampedPathEffectStyle.Rotate // .Morph.translate
 *                 )
 *             )
 *         )
 *
 *
 *    Chained path effect
 *
 *    PathEffect.chainPathEffect() // to combine paths
 *
 * **/

@Composable
fun PathEffectsSample(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(60000, easing = LinearEasing)
        )
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(100f, 100f)
            cubicTo(100f, 300f, 600f, 700f, 600f, 1100f)
            lineTo(800f, 800f)
            lineTo(1000f, 1100f)
        }

        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 5.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(
                    radius = 1000f
                )
            )
        )
    }
}

@Preview(showBackground = true, device = PIXEL_7_PRO, showSystemUi = true)
@Composable
private fun CanvasPathBasicPreview() {
    //CanvasPathBasic()
    //PathOperations()
    //PathAnimation()
    //PathEffectsSample()
    DrawTextOnPath()
}

@Composable
fun ComposeClockScreen() {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = Calendar.getInstance()
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {

                Text("Compose clock", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(48.dp))
            }

            ComposeClock(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                currentTime = currentTime
            )
        }
    }
}

@Composable
fun ScaleMeasureScreen() {
    val scaleStyle = ScaleStyle(scaleWidth = 150.dp)
    val initialWeight = 40
    var selectedWeight by remember { mutableStateOf(initialWeight) }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                val weightSpannedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 44.sp
                        )
                    ) {
                        append("$selectedWeight")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = DarkGreen,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(" KG")
                    }
                }
                Text("Select your weight", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    weightSpannedString
                )
            }

            Scale(
                style = scaleStyle,
                initialWeight = initialWeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.BottomCenter)
            ) {
                selectedWeight = it
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvasComposeMasterTheme {
        Greeting("Android")
    }
}