package com.androidace.canvascomposemaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.buildSpannedString
import com.androidace.canvascomposemaster.ui.theme.CanvasComposeMasterTheme
import com.androidace.canvascomposemaster.ui.theme.DarkGreen
import com.androidace.canvascomposemaster.weightpicker.Scale
import com.androidace.canvascomposemaster.weightpicker.ScaleStyle
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasComposeMasterTheme {
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
                                withStyle(style = SpanStyle(color = DarkGreen, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)) {
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